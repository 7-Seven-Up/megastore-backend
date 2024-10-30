package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/orders")
public interface IOrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest);

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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<OrderResponse> getOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date startPeriodDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date endPeriodDate,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) String state);
}
