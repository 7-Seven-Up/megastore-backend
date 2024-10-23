package com._up.megastore.controllers.responses;

import java.util.UUID;

public record OrderDetailResponse(
        UUID orderDetailId,
        Integer quantity,
        ProductResponse product
) {}