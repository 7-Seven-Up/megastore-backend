package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
}