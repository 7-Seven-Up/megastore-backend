package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity(name = "orderDetails")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDetail {

    @NonNull
    private Integer quantity;

    @NonNull
    private Double priceToDate;

    @ManyToOne @NonNull
    private Product product;

    @ManyToOne @NonNull
    private Order order;

    private boolean deleted = false;

    @Id
    private final UUID orderDetailId = UUID.randomUUID();

}