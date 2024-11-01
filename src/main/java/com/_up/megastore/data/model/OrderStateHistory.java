package com._up.megastore.data.model;

import com._up.megastore.data.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "orderStateHistories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderStateHistory {
    @ManyToOne
    private Order order;

    @ManyToOne
    private User user;

    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = null;

    @Id
    private final UUID orderStateHistoryId = UUID.randomUUID();
}
