package com._up.megastore.services.implementations;

import com._up.megastore.data.enums.State;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderStateHistory;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IOrderStateHistoryRepository;
import com._up.megastore.services.interfaces.IOrderStateHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderStateHistoryService implements IOrderStateHistoryService {
    private final IOrderStateHistoryRepository orderStateHistoryRepository;

    public OrderStateHistoryService(IOrderStateHistoryRepository orderStateHistoryRepository) {
        this.orderStateHistoryRepository = orderStateHistoryRepository;
    }

    @Override
    public void addOrderStateHistory(Order order, State state, User user) {
        orderStateHistoryRepository.save(new OrderStateHistory(order, user, LocalDate.now(), state));
    }
}
