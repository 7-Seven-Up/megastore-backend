package com._up.megastore.controllers.responses;

import com._up.megastore.data.enums.Role;
import java.util.UUID;

public record UserResponse(
    UUID userId,
    String username,
    String fullName,
    String email,
    String phoneNumber,
    Role role
) {

}
