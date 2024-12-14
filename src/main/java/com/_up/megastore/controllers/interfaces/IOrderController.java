package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequestMapping("/api/v1/orders")
public interface IOrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<OrderResponse> getOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int pageSize
    );

    @PostMapping("/{orderId}/finish")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse finishOrder(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/mark-in-delivery")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse markOrderInDelivery(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/deliver")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse deliverOrder(@PathVariable UUID orderId);

    @PostMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    OrderResponse cancelOrder(
            @PathVariable UUID orderId,
            @RequestBody @Valid CancelOrderRequest cancelOrderRequest
    );
}
