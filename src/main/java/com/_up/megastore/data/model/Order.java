package com._up.megastore.data.model;

import com._up.megastore.data.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {

    @ManyToOne
    @NonNull
    private User user;

    @NonNull
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.IN_PROGRESS;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderDetail> orderDetails = Collections.emptyList();

    @Builder.Default
    private LocalDate date = LocalDate.now();

    private String reasonToCancel = null;

    private boolean deleted = false;

    @Id
    private final UUID orderId = UUID.randomUUID();

    // TODO : implement a dates for each state (auditory)
}