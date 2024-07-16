package com.forohub.api.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseDTO(@NotBlank(message = "required") String message, UUID topicId, @NotBlank(message = "solution is required") String solution  ) {
}
