package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Category;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findCategoryByDeletedIsFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByNameAndDeletedIsFalse(String name);

    Page<Category> findCategoriesByDeletedIsTrue(Pageable pageable);
}