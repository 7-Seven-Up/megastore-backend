package com._up.megastore.data.model;

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

    @NonNull
    private Integer number;

    @NonNull
    private Double total;

    @ManyToOne @NonNull
    private User user;

    @ManyToOne @NonNull
    private State state;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderDetail> orderDetails = Collections.emptyList();

    private LocalDate date = LocalDate.now();
    private boolean deleted = false;

    @Id
    private final UUID orderId = UUID.randomUUID();

}