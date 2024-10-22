package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull(message = "User ID must not be null")
        UUID userId,

        @NotNull(message = "Order details must not be null")
        @Size(min = 1, message = "Order detail list must have at least one product")
        List<OrderDetailRequest> orderDetailRequestList
) {
}