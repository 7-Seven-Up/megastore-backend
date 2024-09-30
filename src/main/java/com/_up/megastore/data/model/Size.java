package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "sizes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Size {

    @NonNull
    private String name;

    private String description;

    private boolean deleted = false;

    @Id
    private final UUID sizeId = UUID.randomUUID();

}