package com._up.megastore.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private Double price;

    @NonNull
    private String imageURL;

    @NonNull
    private Integer stock;

    @NonNull
    private String color;

    @ManyToOne @NonNull
    private Size size;

    @ManyToOne @NonNull
    private Category category;

    @ManyToOne
    private Product variantOf = null;

    private boolean deleted = false;

    @OneToMany(mappedBy = "productId", cascade = {CascadeType.ALL})
    private List<Product> variants = Collections.emptyList();

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = Collections.emptyList();

    @Id
    private final UUID productId = UUID.randomUUID();
}