package com.forohub.api.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank(message = "Email is require") String email, @NotBlank(message = "Password required") String password) {
}
