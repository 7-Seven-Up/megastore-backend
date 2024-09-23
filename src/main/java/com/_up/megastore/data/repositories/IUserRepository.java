package com._up.megastore.data.repositories;

import com._up.megastore.data.model.User;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, UUID> {

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  Optional<User> findByUserIdAndActivatedIsFalse(UUID userId);
}