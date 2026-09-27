package com.restaurant.user_service.presentation.dto;

import com.restaurant.user_service.domain.model.Role;

import java.util.UUID;

public record UserAuthenticationResponse(
        UUID id,
        String email,
        String password,
        Role role,
        boolean active
) {
}