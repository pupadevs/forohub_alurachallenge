package com.forohub.api.app.service;

import com.forohub.api.domain.dto.ResponseDTO;
import com.forohub.api.domain.dto.topic.ShowTopicDTO;
import com.forohub.api.domain.dto.topic.TopicDTO;
import com.forohub.api.domain.dto.topic.UpdateTopicDTO;
import com.forohub.api.domain.entity.Topic;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.TopicRepository;
import com.forohub.api.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;
    @Autowired
    private UserRepository userRepository;

    public ShowTopicDTO createTopic(TopicDTO data){
        User user = userRepository.getReferenceById(UUID.fromString(data.uuid()));
       var topic = new Topic(data.title(),data.message());
       topic.setCreationDate(LocalDateTime.now());
       topic.setStatus(true);
       topic.setUser(user);
        
        repository.save(topic);
//        List<ResponseDTO> responses = topic.getResponses() == null ?
//                List.of() :
//                topic.getResponses().stream()
//                        .map(response -> new ResponseDTO(
//                                response.getMessage(),
//                                response.getCreationDate(),
//                                response.getUser() != null ? response.getUser().getName() : null,
//                                response.getSolution())).collect(Collectors.toList());
//        ShowTopicDTO topicDTO = new ShowTopicDTO(topic.getId().toString(),
//                topic.getTitle(), topic.getMessage(),
//                topic.getUser().getName(),responses,topic.getCreationDate()
//            );

        return converToDto(topic);
    }

    public Stream<ShowTopicDTO> show(){
        List<Topic> topics = repository.findAll();

        return  topics.stream().map(topic -> new ShowTopicDTO(topic.getId().toString(),topic.getTitle(),
                topic.getMessage(),
                topic.getUser().getName(),topic.getResponses().stream()
                .map(response -> new ResponseDTO(
                        response.getMessage(),
                        response.getCreationDate(),
                        response.getUser() != null ? response.getUser().getName() : null,
                        response.getSolution())).collect(Collectors.toList()),topic.getCreationDate()));
    }

    public  ShowTopicDTO findTopic(UUID id){
        Topic topic = repository.getReferenceById(id);

        return converToDto(topic);
    }

    public ShowTopicDTO update(UUID id, UpdateTopicDTO topicDTO){
        Topic topic = repository.getReferenceById(id);
        topic.setTitle(topicDTO.title());
        topic.setMessage(topicDTO.message());
        repository.save(topic);


        return converToDto(topic);
    }

    public void deleteTopic(UUID id){
        Topic topic = repository.getReferenceById(id);
        repository.delete(topic);

    }

    private ShowTopicDTO converToDto(Topic topic){
        return  new ShowTopicDTO(
                topic.getId().toString(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getUser().getName(),
                topic.getResponses().stream().map(re -> new ResponseDTO(
                        re.getMessage(),
                        re.getCreationDate(),
                        re.getUser() != null ? re.getUser().getName() : null,
                        re.getSolution())).collect(Collectors.toList()),topic.getCreationDate());

    }
}
