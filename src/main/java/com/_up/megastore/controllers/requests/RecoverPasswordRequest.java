package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record RecoverPasswordRequest(
        @NotBlank(message = "Password must not be null")
        @Length(min = 6, max = 20)
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!¡¿?$%&#@_-])[A-Za-z0-9!¡¿?$%&#@_-]+$", message = "The password are not strong.")
        String password,
        String confirmPassword,
        @NotBlank(message = "RecoverPasswordToken must not be null")
        UUID recoverPasswordToken
) {}
