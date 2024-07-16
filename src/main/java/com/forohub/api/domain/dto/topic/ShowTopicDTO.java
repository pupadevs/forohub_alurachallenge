package com.forohub.api.domain.dto.topic;

import com.forohub.api.domain.dto.response.ResponseDTO;
import com.forohub.api.domain.dto.response.ShowResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ShowTopicDTO(
                           String topicId,
                           String title,
                           String message,
                           String author,
                           List<ShowResponseDTO> responses,
                           LocalDateTime creationDate)
{
}
