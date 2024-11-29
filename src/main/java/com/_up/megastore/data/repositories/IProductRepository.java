package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findProductsByDeletedIsFalseAndVariantOfIsNullAndNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findProductsByDeletedIsFalseAndNameContainingIgnoreCase(String name,
        Pageable pageable);
    boolean existsByVariantOfAndDeletedFalse(Product variantOf);
    List<Product> findProductByVariantOfAndDeletedFalse(Product variantOf);
    Page<Product> findProductsByDeletedIsTrue(Pageable pageable);
}