package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IOrderController;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.services.interfaces.IOrderService;
import org.springframework.web.bind.annotation.RestController;

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

}