package com.forohub.api.app.service;

import com.forohub.api.app.service.user.NoAuthorizedException;
import com.forohub.api.domain.dto.response.ResponseDTO;
import com.forohub.api.domain.dto.response.ShowResponseDTO;
import com.forohub.api.domain.dto.response.UpdateResponseDTO;
import com.forohub.api.domain.entity.Response;
import com.forohub.api.domain.entity.Topic;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.ResponseRepository;
import com.forohub.api.infrastructure.repository.TopicRepository;
import com.forohub.api.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private  UserRepository userRepository;


    public ShowResponseDTO createResponse( ResponseDTO responseDTO, String token){
      Topic topic =  topicRepository.getReferenceById(responseDTO.topicId());
        String userID = tokenService.extractUserIdFromToken(token.substring(7));
        User user = userRepository.getReferenceById(UUID.fromString(userID));
        Response response = new Response();
        response.setTopic(topic);
        response.setUser(user);
        response.setMessage(responseDTO.message());
        response.setSolution(responseDTO.solution());
        responseRepository.save(response);
        return new ShowResponseDTO(response.getId(),response.getUser().getName(), response.getMessage(), LocalDateTime.now(), response.getSolution());
    }

    public List<ShowResponseDTO> showAll(){
        List<ShowResponseDTO> responses = responseRepository.findAll().stream().map(re -> new ShowResponseDTO(
                re.getId(),
                re.getUser().getName(),
                re.getMessage(),
                re.getCreationDate(),
                re.getSolution())).collect(Collectors.toList());
        return responses;
    }

    public ShowResponseDTO updateResponse(UUID responseID,UpdateResponseDTO updateResponseDTO, String token){
        Response response = responseRepository.getReferenceById(responseID);
      String userId=  tokenService.getSubject(token.substring(7));
      if(response.getUser().getId().equals(userId)){
          throw new NoAuthorizedException();

      }
      response.setMessage(updateResponseDTO.message());
        response.setSolution(updateResponseDTO.solution());
        responseRepository.save(response);

        return new ShowResponseDTO(response.getId(),response.getUser().getName()
                ,response.getMessage(),response.getCreationDate(),response.getSolution());
    }
}
