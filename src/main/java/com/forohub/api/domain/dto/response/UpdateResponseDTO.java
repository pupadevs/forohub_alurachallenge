package com.forohub.api.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

public record UpdateResponseDTO(@NotBlank(message = "message is required") String message, @NotBlank(message = "Solution is required") String solution) {
}
