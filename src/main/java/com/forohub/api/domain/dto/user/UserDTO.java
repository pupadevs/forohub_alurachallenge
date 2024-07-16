package com.forohub.api.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Name is required")
                      String name,
                      @NotBlank @Email
                      String email,
                   @NotBlank(message = "Password is required")
                      @Size(min = 5, message = "Password must be at least 5 characters long")
                      String password) {
}
