package com.forohub.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ResponseDTO(String message, LocalDateTime creationDate, @NotBlank String author,@NotBlank String solution  ) {
}
