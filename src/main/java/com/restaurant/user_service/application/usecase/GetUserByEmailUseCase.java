package com.restaurant.user_service.application.usecase;

import com.restaurant.user_service.domain.model.User;

public interface GetUserByEmailUseCase {

    User getByEmail(String email);
}