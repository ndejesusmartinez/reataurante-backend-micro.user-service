package com.restaurant.user_service.presentation.controller;

import com.restaurant.user_service.application.usecase.CreateUserUseCase;
import com.restaurant.user_service.domain.model.User;
import com.restaurant.user_service.presentation.dto.CreateUserRequest;
import com.restaurant.user_service.presentation.dto.UserResponse;
import com.restaurant.user_service.presentation.dto.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.restaurant.user_service.application.usecase.GetUsersUseCase;
import com.restaurant.user_service.application.usecase.GetUserByIdUseCase;
import com.restaurant.user_service.application.usecase.UpdateUserUseCase;
import com.restaurant.user_service.application.usecase.DeleteUserUseCase;
import com.restaurant.user_service.application.usecase.GetUserByEmailUseCase;
import java.util.List;
import java.util.UUID;
import com.restaurant.user_service.presentation.dto.UserAuthenticationResponse;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUsersUseCase  getUsersUseCase,
            GetUserByIdUseCase  getUserByIdUseCase,
            UpdateUserUseCase  updateUserUseCase,
            DeleteUserUseCase  deleteUserUseCase,
            GetUserByEmailUseCase  getUserByEmailUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        User user = createUserUseCase.create(
                request.name(),
                request.email(),
                request.password(),
                request.role()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return getUsersUseCase.getAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.isActive(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ))
                .toList();
        }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable UUID id) {               
    User user = getUserByIdUseCase.getById(id);            
    return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.isActive(),
            user.getCreatedAt(),
            user.getUpdatedAt()
    );
    }

    @PutMapping("/{id}")
        public UserResponse updateUser(
                @PathVariable UUID id,
                @Valid @RequestBody UpdateUserRequest request
    ) {
        User user = updateUserUseCase.update(
                id,
                request.name(),
                request.email(),
                request.role(),
                request.active()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.delete(id);
    }

    @GetMapping("/internal/by-email")
        public UserAuthenticationResponse getUserByEmail(
                @RequestParam String email
        ) {
                User user = getUserByEmailUseCase.getByEmail(email);
                return new UserAuthenticationResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        user.isActive()
                );
        }
}