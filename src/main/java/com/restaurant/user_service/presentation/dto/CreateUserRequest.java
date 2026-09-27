package com.restaurant.user_service.presentation.dto;

import com.restaurant.user_service.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150)
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "El password es obligatorio")
        @Size(min = 6)
        String password,

        @NotNull(message = "El rol es obligatorio")
        Role role
) {
}