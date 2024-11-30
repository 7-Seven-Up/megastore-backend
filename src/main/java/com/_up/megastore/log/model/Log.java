package com._up.megastore.log.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public record Log(
        UUID id,
        String username,
        String httpMethod,
        String requestBody,
        String endpoint,
        LocalDateTime date
) {
}
