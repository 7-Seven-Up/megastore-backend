package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "states")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class State {

    @NonNull
    private String name;

    @NonNull
    private String description;

    private boolean deleted = false;

    @Id
    private final UUID stateId = UUID.randomUUID();

}