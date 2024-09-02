package com._up.megastore.data.repositories;

import com._up.megastore.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}