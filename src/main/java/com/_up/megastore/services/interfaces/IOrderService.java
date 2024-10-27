package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.model.Order;

import java.util.UUID;

public interface IOrderService {

    OrderResponse saveOrder(CreateOrderRequest createOrderRequest);

    Order findOrderByIdOrThrowException(UUID orderId);

    OrderResponse finishOrder(UUID orderId);

    OrderResponse markOrderInDelivery(UUID orderId);

    OrderResponse deliverOrder(UUID orderId);
}
