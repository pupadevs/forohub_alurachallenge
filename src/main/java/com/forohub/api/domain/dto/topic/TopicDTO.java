package com.forohub.api.domain.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicDTO(@NotBlank(message = "title is requires") String title,
                       @NotBlank(message = "message is required") String message,
                       @NotBlank(message = "user id is required")  String uuid) {
}
