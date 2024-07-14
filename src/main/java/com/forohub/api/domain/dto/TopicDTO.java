package com.forohub.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TopicDTO(@NotBlank(message = "title is requires") String title,
                       @NotBlank(message = "message is required") String message,
                     @NotBlank(message = "user id is required")  String uuid) {
}
