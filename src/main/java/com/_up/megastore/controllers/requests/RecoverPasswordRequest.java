package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record RecoverPasswordRequest(
        @NotBlank(message = "Password must not be null")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!¡¿?$%&#@_-])[A-Za-z0-9!¡¿?$%&#@_-]+$", message = "The password are not strong.")
        String password,
        @NotBlank(message = "Confirm Password must not be null")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!¡¿?$%&#@_-])[A-Za-z0-9!¡¿?$%&#@_-]+$", message = "The password are not strong.")
        String confirmPassword,
        @NotBlank(message = "RecoverPasswordToken must not be null")
        UUID recoverPasswordToken
)
{}
