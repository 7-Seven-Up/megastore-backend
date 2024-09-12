package com._up.megastore.data.repositories;

import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findCategoryByDeletedIsFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
}