package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IOrderController;
import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.services.interfaces.IOrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController implements IOrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        return orderService.saveOrder(createOrderRequest);
    }

    @Override
    public OrderResponse finishOrder(UUID orderId) {
        return orderService.finishOrder(orderId);
    }

    @Override
    public OrderResponse markOrderInDelivery(UUID orderId) {
        return orderService.markOrderInDelivery(orderId);
    }

    @Override
    public OrderResponse deliverOrder(UUID orderId) {
        return orderService.deliverOrder(orderId);
    }

    @Override
    public OrderResponse cancelOrder(UUID orderId, CancelOrderRequest cancelOrderRequest) {
        return orderService.cancelOrder(orderId, cancelOrderRequest);
    }

    @Override
    public List<OrderResponse> getOrders(int page, int pageSize, Date startPeriodDate, Date endPeriodDate, UUID userId, String state) {
        return orderService.getOrders(page, pageSize, startPeriodDate, endPeriodDate, userId, state);
    }

    @Override
    public OrderResponse getOrder(UUID orderId) {
        return orderService.getOrder(orderId);
    }
}