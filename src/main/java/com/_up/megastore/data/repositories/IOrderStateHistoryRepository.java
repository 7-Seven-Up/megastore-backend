package com._up.megastore.data.repositories;

import com._up.megastore.data.model.OrderStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderStateHistoryRepository extends JpaRepository<OrderStateHistory, UUID> {
}
