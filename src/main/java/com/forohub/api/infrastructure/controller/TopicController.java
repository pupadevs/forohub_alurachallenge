package com.forohub.api.infrastructure.controller;

import com.forohub.api.app.service.TopicService;
import com.forohub.api.domain.dto.TopicDTO;
import com.forohub.api.domain.entity.Topic;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {
@Autowired
    private TopicService topic;

    @PostMapping
    public ResponseEntity createTopic(@RequestBody @Valid TopicDTO data){
        Topic newTopic = topic.createTopic(data);
        return ResponseEntity.ok().body(topic);

    }
}
