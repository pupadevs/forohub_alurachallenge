package com.forohub.api.infrastructure.controller;

import com.forohub.api.app.service.TopicService;
import com.forohub.api.domain.dto.topic.ShowTopicDTO;
import com.forohub.api.domain.dto.topic.TopicDTO;
import com.forohub.api.domain.dto.topic.UpdateTopicDTO;
import com.forohub.api.domain.entity.Topic;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/topic")
public class TopicController {
@Autowired
    private TopicService topic;

    @PostMapping
    public ResponseEntity<ShowTopicDTO> createTopic(@RequestBody @Valid TopicDTO data){
        ShowTopicDTO newTopic = topic.createTopic(data);
        return ResponseEntity.ok(newTopic);

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTopic(@PathVariable UUID id){
        topic.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShowTopicDTO> updateTopic(@PathVariable UUID id, @RequestBody @Valid UpdateTopicDTO topicDTO){
        ShowTopicDTO updateTopic = topic.update(id, topicDTO);
        return ResponseEntity.ok(updateTopic);
    }
}
