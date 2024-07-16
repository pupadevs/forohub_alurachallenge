package com.forohub.api.infrastructure.controller;

import com.forohub.api.app.service.topic.TopicService;
import com.forohub.api.domain.dto.topic.ShowTopicDTO;
import com.forohub.api.domain.dto.topic.TopicDTO;
import com.forohub.api.domain.dto.topic.UpdateTopicDTO;
import com.forohub.api.domain.entity.Topic;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/topic")
public class TopicController {
@Autowired
    private TopicService topic;


    @PostMapping
    @Transactional
    public ResponseEntity<ShowTopicDTO> createTopic(@RequestBody @Valid TopicDTO data, UriComponentsBuilder uriComponentsBuilder){
        ShowTopicDTO newTopic = topic.createTopic(data);
        URI url = uriComponentsBuilder.path("/topic/show/{id}").buildAndExpand(newTopic.topicId()).toUri();
        return ResponseEntity.created(url).body(newTopic);

    }

    @GetMapping
    public ResponseEntity<Stream<ShowTopicDTO>> showAllTopics(){
        Stream<ShowTopicDTO> topics = topic.show();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("show/{id}")
    public ResponseEntity showTopic(@PathVariable UUID id){
        ShowTopicDTO data = topic.findTopic(id);
        return ResponseEntity.ok(data);
    }
@Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTopic(@PathVariable UUID id, @RequestHeader("Authorization") String token){
        topic.deleteTopic(id,token);
        return ResponseEntity.noContent().build();
    }
@Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<ShowTopicDTO> updateTopic(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateTopicDTO topicDTO,
             @RequestHeader("Authorization") String token
                                                    ){
        ShowTopicDTO updateTopic = topic.update(id, topicDTO,token);
        return ResponseEntity.ok(updateTopic);
    }
}
