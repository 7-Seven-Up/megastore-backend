package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByProductIdAndDeletedTrue(UUID productId);
    Page<Product> findProductsByDeletedIsFalseAndNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByNameIgnoreCaseAndDeletedIsFalse(String name);
    boolean existsByVariantOfAndDeletedFalse(Product variantOf);
    List<Product> findProductByVariantOfAndDeletedFalse(Product variantOf);
}