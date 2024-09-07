package com._up.megastore.controllers.requests;

import com._up.megastore.data.pipes.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
    @NotNull(message = "User email must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "User email must be a valid email")
    String email,

    @NotNull(message = "User full name must not be null")
    @Size(min = 3, max = 20, message = "User full name must be between 3 and 20 characters")
    String fullName,

    @NotNull(message = "User password must not be null")
    @Size(min = 6, max = 20, message = "User password must be between 6 and 20 characters")
    String password,

    @NotBlank(message = "User phone number must not be blank")
    @PhoneNumber(message = "User phone number must be a valid phone number")
    String phoneNumber,

    @NotNull(message = "User username must not be null")
    @Size(min = 3, max = 20, message = "User username must be between 3 and 20 characters")
    String username
) {

}