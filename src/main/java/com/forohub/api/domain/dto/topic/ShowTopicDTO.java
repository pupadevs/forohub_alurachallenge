package com.forohub.api.domain.dto.topic;

import com.forohub.api.domain.dto.ResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ShowTopicDTO(
                           String topicId,
                           String title,
                           String message,
                           String author,
                           List<ResponseDTO> responses,
                           LocalDateTime creationDate)
{
}
