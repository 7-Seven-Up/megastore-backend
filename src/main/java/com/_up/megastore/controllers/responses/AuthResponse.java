package com._up.megastore.controllers.responses;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String accessToken
) {}