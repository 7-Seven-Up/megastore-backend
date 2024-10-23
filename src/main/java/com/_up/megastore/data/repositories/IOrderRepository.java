package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT COALESCE(MAX(o.number), 1) FROM orders o")
    Integer getLastOrderNumber();
}