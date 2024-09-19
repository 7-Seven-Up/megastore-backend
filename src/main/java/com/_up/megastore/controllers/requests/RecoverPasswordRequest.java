package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RecoverPasswordRequest(
        @NotNull(message = "Password must not be null")
        String password,
        @NotNull(message = "Confirm Password must not be null")
        String confirmPassword,
        @NotNull(message = "RecoverPasswordToken must not be null")
        UUID recoverPasswordToken
)
{}
