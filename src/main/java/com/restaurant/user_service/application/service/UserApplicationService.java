package com.restaurant.user_service.application.service;

import com.restaurant.user_service.application.usecase.CreateUserUseCase;
import com.restaurant.user_service.domain.model.Role;
import com.restaurant.user_service.domain.model.User;
import com.restaurant.user_service.domain.port.UserRepository;
import com.restaurant.user_service.exception.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.restaurant.user_service.application.usecase.GetUsersUseCase;
import com.restaurant.user_service.application.usecase.GetUserByIdUseCase;
import com.restaurant.user_service.application.usecase.UpdateUserUseCase;
import com.restaurant.user_service.application.usecase.DeleteUserUseCase;
import com.restaurant.user_service.application.usecase.GetUserByEmailUseCase;
import java.util.List;
import com.restaurant.user_service.exception.UserNotFoundException;
import java.util.UUID;

@Service
public class UserApplicationService implements CreateUserUseCase, GetUsersUseCase, GetUserByIdUseCase, UpdateUserUseCase, DeleteUserUseCase, GetUserByEmailUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserApplicationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(
            String name,
            String email,
            String password,
            Role role
    ) {

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "El email ya está registrado"
            );
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.create(
                name,
                email,
                encodedPassword,
                role
        );

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Usuario no encontrado"
                        )
                );
    }

    @Override
        public User update(
                UUID id,
                String name,
                String email,
                Role role,
                boolean active
        ) {
                User user = userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

                if (!user.getEmail().equalsIgnoreCase(email)
                        && userRepository.existsByEmail(email)) {

                        throw new EmailAlreadyExistsException(
                                "El email ya está registrado"
                        );
                }

                user.update(
                        name,
                        email,
                        role,
                        active
                );

                return userRepository.save(user);
        }

     @Override
        public void delete(
                UUID id
        ) {
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

                userRepository.deleteById(id);

        }

     @Override
        public User getByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Usuario no encontrado"));
        }
}