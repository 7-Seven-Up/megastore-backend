package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.Email;

public record SendRecoverPasswordEmailRequest(
   @Email(message = "Invalid email")
   String email
) {}
