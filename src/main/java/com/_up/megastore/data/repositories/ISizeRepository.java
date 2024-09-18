package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISizeRepository extends JpaRepository<Size, UUID> {
    boolean existsByName(String name);
    Page<Size> findSizeByDeletedIsFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
}