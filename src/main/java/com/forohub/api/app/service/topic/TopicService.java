package com.forohub.api.app.service.topic;

import com.forohub.api.app.service.ResourceNotFoundException;
import com.forohub.api.app.service.TokenService;
import com.forohub.api.app.service.user.NoAuthorizedException;
import com.forohub.api.domain.dto.response.ResponseDTO;
import com.forohub.api.domain.dto.response.ShowResponseDTO;
import com.forohub.api.domain.dto.topic.ShowTopicDTO;
import com.forohub.api.domain.dto.topic.TopicDTO;
import com.forohub.api.domain.dto.topic.UpdateTopicDTO;
import com.forohub.api.domain.entity.Topic;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.TopicRepository;
import com.forohub.api.infrastructure.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public ShowTopicDTO createTopic(TopicDTO data){
        User user = userRepository.getReferenceById(UUID.fromString(data.uuid()));
        var topic = new Topic(data.title(),data.message());
        topic.setCreationDate(LocalDateTime.now());
        topic.setStatus(true);
        topic.setUser(user);

        topicRepository.save(topic);
        List<ShowResponseDTO> responses = topic.getResponses() == null ?
                List.of() :
                topic.getResponses().stream()
                        .map(response -> new ShowResponseDTO(
                                response.getId(),
                                response.getMessage(),
                                response.getUser().getName(),

                                response.getCreationDate(),

                                response.getSolution())).collect(Collectors.toList());
        System.out.println(topic.getId());

        ShowTopicDTO topicDTO = new ShowTopicDTO(topic.getId().toString(),
                topic.getTitle(), topic.getMessage(),
                topic.getUser().getName(),responses,topic.getCreationDate()
        );

        return topicDTO;
    }

    public Stream<ShowTopicDTO> show(){
        List<Topic> topics = topicRepository.findAll();

        return  topics.stream().map(topic -> new ShowTopicDTO(topic.getId().toString(),topic.getTitle(),
                topic.getMessage(),
                topic.getUser().getName(),topic.getResponses().stream()
                .map(response -> new ShowResponseDTO(
                        response.getId(),
                        response.getUser().getName(),
                        response.getMessage(),
                        response.getCreationDate(),
                        response.getSolution())).collect(Collectors.toList()),topic.getCreationDate()));
    }

    public  ShowTopicDTO findTopic(UUID id){
        try{
            Topic topic = topicRepository.getReferenceById(id);


            return converToDto(topic);
        }catch (EntityNotFoundException entity){
            throw new ResourceNotFoundException("Topic no encontrado con ID: " + id);
        }

    }

    public ShowTopicDTO update(UUID id, UpdateTopicDTO topicDTO, String token){
        try{
            Topic topic = topicRepository.getReferenceById(id);

            String subject = tokenService.extractUserIdFromToken(token.substring(7));
            System.out.println("User id: " +subject + "/n" );
            System.out.println(id + ":topicid");
            System.out.println(topic.getId() + ":topicid");
            System.out.println( topic.getUser().getId()+  ":user_id");
            System.out.println(subject);
            System.out.println(topic.getUser());

            verifyAuthorization(token,topic.getUser().getId().toString());
            topic.setTitle(topicDTO.title());
            topic.setMessage(topicDTO.message());
            topicRepository.save(topic);
            return converToDto(topic);

        }catch (EntityNotFoundException entity){
            throw new ResourceNotFoundException("Topic no encontrado con ID: " + id);

        }

    }

    public void deleteTopic(UUID id, String token){
        try{
            Topic topic = topicRepository.getReferenceById(id);
            verifyAuthorization(token,topic.getUser().getId().toString());
            topicRepository.delete(topic);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Topic no encontrado con ID: " + id);


        }


    }

    private void verifyAuthorization(String token, String id){

        String userID = tokenService.extractUserIdFromToken(token.substring(7));

        if(!userID.equals(id)){
            throw new NoAuthorizedException();
        }

    }

    private ShowTopicDTO converToDto(Topic topic){
        return  new ShowTopicDTO(
                topic.getId().toString(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getUser().getName(),
                topic.getResponses().stream().map(re -> new ShowResponseDTO(
                        re.getId(),
                        re.getMessage(),
                        re.getUser().getName(),
                        re.getCreationDate(),
                        re.getSolution())).collect(Collectors.toList()),topic.getCreationDate());

    }
}
