package com._up.megastore.data.repositories;

import com._up.megastore.data.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, UUID> {}