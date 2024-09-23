package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Token {
    @Id
    private final UUID tokenId = UUID.randomUUID();

    private LocalDateTime tokenExpirationDate = LocalDateTime.now().plusHours(24);

    @ManyToOne
    private User user;

}
