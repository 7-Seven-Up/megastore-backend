package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RecoverPasswordRequest(
        @NotBlank(message = "Password must not be null")
        String password,
        @NotBlank(message = "Confirm Password must not be null")
        String confirmPassword,
        @NotBlank(message = "RecoverPasswordToken must not be null")
        UUID recoverPasswordToken
)
{}
