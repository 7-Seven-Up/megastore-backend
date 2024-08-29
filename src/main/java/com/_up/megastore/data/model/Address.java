package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Address {

    @NonNull
    private String street;

    @NonNull
    private String department;

    @NonNull
    private Integer number;

    @ManyToOne @NonNull
    private User user;

    private boolean deleted = false;

    @Id
    private UUID addressId = UUID.randomUUID();

}