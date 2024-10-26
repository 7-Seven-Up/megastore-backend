package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequestMapping("/api/v1/orders")
public interface IOrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse saveOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @PostMapping("/{orderId}/finish")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse finishOrder(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/mark-in-delivery")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse markOrderInDelivery(@PathVariable UUID orderId);

}
