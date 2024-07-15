package com.forohub.api.domain.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicDTO(@NotBlank(message = "title is requires")String title,@NotBlank(message = "message is requires") String message) {
}
