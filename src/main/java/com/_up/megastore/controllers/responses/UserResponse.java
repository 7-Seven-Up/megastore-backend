package com._up.megastore.controllers.responses;

import java.util.UUID;

public record UserResponse(
    UUID userId,
    String username,
    String fullName,
    String email,
    String phoneNumber
) {

}
