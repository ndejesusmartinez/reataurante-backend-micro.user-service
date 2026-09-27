package com.restaurant.user_service.domain.port;

import com.restaurant.user_service.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    List<User> findAll();

    Optional<User> findById(UUID id);

    void deleteById(UUID id);

    Optional<User> findByEmail(String email);
}