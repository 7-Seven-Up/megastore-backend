package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Page<Product> findProductsByDeletedIsFalse(Pageable pageable);
    Optional<Product> findProductByProductIdAndDeletedIsFalse(UUID productId);
}