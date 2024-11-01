package com._up.megastore.services.interfaces;

import com._up.megastore.data.enums.State;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.User;

public interface IOrderStateHistoryService {
    void addOrderStateHistory(Order order, State state, User user);
}
