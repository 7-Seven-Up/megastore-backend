package com._up.megastore.controllers.responses;

import com._up.megastore.data.enums.Role;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String username,
        String accessToken,
        Role role
) {}