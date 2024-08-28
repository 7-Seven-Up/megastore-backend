package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {}