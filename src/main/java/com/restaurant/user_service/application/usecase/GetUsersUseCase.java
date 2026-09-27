package com.restaurant.user_service.application.usecase;

import com.restaurant.user_service.domain.model.User;
import java.util.List;

public interface GetUsersUseCase {

    List<User> getAll();
}