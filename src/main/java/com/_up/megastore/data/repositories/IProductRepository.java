package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findProductsByDeletedIsFalseAndNameLikeIgnoreCase(String filter, Pageable pageable);
    Optional<Product> findProductByProductIdAndDeletedIsFalse(UUID productId);
}