package com.forohub.api.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(@NotBlank(message = "Name is required") String name, @NotBlank(message = "Email is required") String email) {
}
