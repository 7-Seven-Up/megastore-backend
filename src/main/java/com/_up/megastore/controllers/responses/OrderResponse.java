package com._up.megastore.controllers.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        Integer number,
        LocalDate date,
        String clientName,
        String state,
        String reasonToCancel,
        Double total,
        List<OrderDetailResponse> orderDetails
) {}