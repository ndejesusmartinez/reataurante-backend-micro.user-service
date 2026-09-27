package com.restaurant.user_service.application.usecase;

import com.restaurant.user_service.domain.model.User;

import java.util.UUID;

public interface UpdateUserUseCase {

   User update(
        UUID id,
        String name,
        String email,
        com.restaurant.user_service.domain.model.Role role,
        boolean active
    );
}