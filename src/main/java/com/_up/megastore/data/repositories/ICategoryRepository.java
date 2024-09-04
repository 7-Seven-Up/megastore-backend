package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByIdAndDeletedIsFalse(UUID categoryId);
}