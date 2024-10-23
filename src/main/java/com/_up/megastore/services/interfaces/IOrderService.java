package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;

public interface IOrderService {

    OrderResponse saveOrder(CreateOrderRequest createOrderRequest);

}
