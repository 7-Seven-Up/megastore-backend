package com._up.megastore.data.model;

import com._up.megastore.data.enums.State;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    @ManyToOne @NonNull
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

    private boolean deleted = false;

    @Id
    private final UUID orderId = UUID.randomUUID();

}