package com.forohub.api.app.service;

import com.forohub.api.domain.dto.ResponseDTO;
import com.forohub.api.domain.dto.ShowTopicDTO;
import com.forohub.api.domain.dto.TopicDTO;
import com.forohub.api.domain.entity.Topic;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.TopicRepository;
import com.forohub.api.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Topic createTopic(TopicDTO data){
        User user = userRepository.getReferenceById(UUID.fromString(data.uuid()));
       var topic = new Topic(data.title(),data.message());
       topic.setCreationDate(LocalDateTime.now());
       topic.setStatus(true);
       topic.setUser(user);
        
        repository.save(topic);
        List<ResponseDTO> responses = topic.getResponses() == null ?
                List.of() :
                topic.getResponses().stream()
                        .map(response -> new ResponseDTO(
                                response.getMessage(),
                                response.getCreationDate(),
                                response.getUser() != null ? response.getUser().getName() : null,
                                response.getSolution())).collect(Collectors.toList());
        ShowTopicDTO topicDTO = new ShowTopicDTO(topic.getId().toString(),
                topic.getTitle(), topic.getMessage(),
                topic.getUser().getName(),responses,topic.getCreationDate()
            );

        return topic;
    }
}
