package com._up.megastore.controllers.responses;

import java.util.List;
import java.util.UUID;

public record SizeResponse(
        UUID sizeId,
        String name,
        String description
) {}