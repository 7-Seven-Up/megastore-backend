package com._up.megastore.data.repositories;

import com._up.megastore.data.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductImageRepository extends JpaRepository<ProductImage, UUID> {}