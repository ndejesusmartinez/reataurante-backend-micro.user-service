package com.restaurant.user_service.application.usecase;

import com.restaurant.user_service.domain.model.User;

public interface CreateUserUseCase {

    User create(
            String name,
            String email,
            String password,
            com.restaurant.user_service.domain.model.Role role
    );
}