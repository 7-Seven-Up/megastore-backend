package com._up.megastore.data.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com._up.megastore.data.model.Product;

public interface IProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findProductsByDeletedIsFalseAndNameContainingIgnoreCase(String filter, Pageable pageable);

    Optional<Product> findProductByProductIdAndDeletedIsFalse(UUID productId);
}
