package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT COALESCE(MAX(o.number), 0) FROM orders o")
    Integer getLastOrderNumber();

    @Query("SELECT SUM(detail.subtotal) FROM orders o JOIN o.orderDetails detail WHERE o = ?1")
    Double getOrderTotal(Order order);

    Page<Order> findAll(Specification<Order> orderSpecification, Pageable pageable);
}