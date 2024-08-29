package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "sizes")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class Size {

    @NonNull
    private String name;

    @NonNull
    private String description;

    private boolean deleted = false;

    @Id
    private UUID sizeId = UUID.randomUUID();

}