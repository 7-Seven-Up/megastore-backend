package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.model.Order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IOrderService {

    OrderResponse saveOrder(CreateOrderRequest createOrderRequest);

    Order findOrderByIdOrThrowException(UUID orderId);

    OrderResponse finishOrder(UUID orderId);

    OrderResponse markOrderInDelivery(UUID orderId);

    OrderResponse deliverOrder(UUID orderId);

    OrderResponse cancelOrder(UUID orderId, CancelOrderRequest cancelOrderRequest);

    List<OrderResponse> getOrders(int page, int pageSize, Date startPeriodDate, Date endPeriodDate, UUID userId, String state);
}
