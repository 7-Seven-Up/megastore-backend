package com._up.megastore.controllers.requests;

import jakarta.validation.constraints.Email;

public record SendEmailRequest(
   @Email(message = "Invalid email")
   String email
) {}