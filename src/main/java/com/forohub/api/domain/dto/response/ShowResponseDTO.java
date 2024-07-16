package com.forohub.api.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShowResponseDTO(
       @NotBlank(message = "is required") UUID responseId,
        String author,
        String message,
        LocalDateTime creationDate,
        String solution) {
}
