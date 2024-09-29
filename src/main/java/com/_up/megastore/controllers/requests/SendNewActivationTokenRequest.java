package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SendNewActivationTokenRequest(
        @NotBlank(message = "Activation token must not be blank")
        UUID activationToken
) {}