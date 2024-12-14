package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.model.Order;

import java.util.UUID;
import org.springframework.data.domain.Page;

public interface IOrderService {

    OrderResponse saveOrder(CreateOrderRequest createOrderRequest);

    Page<OrderResponse> getOrders(int page, int pageSize);

    Order findOrderByIdOrThrowException(UUID orderId);

    OrderResponse finishOrder(UUID orderId);

    OrderResponse markOrderInDelivery(UUID orderId);

    OrderResponse deliverOrder(UUID orderId);

    OrderResponse cancelOrder(UUID orderId, CancelOrderRequest cancelOrderRequest);
}
