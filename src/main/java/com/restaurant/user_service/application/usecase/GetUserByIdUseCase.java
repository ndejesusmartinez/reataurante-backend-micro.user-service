package com.restaurant.user_service.application.usecase;

import com.restaurant.user_service.domain.model.User;

import java.util.UUID;

public interface GetUserByIdUseCase {

    User getById(UUID id);
}