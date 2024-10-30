package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RecoverPasswordRequest(

        @NotNull(message = "User password must not be null")
        @Size(min = 6, max = 20, message = "User password must be between 6 and 20 characters")
        String password,
        String confirmPassword,

        @NotBlank(message = "RecoverPasswordToken must not be null")
        UUID recoverPasswordToken
) {}
