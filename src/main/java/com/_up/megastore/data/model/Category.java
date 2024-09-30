package com._up.megastore.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Category {

    @NonNull
    private String name;

    private String description;

    @ManyToOne
    private Category superCategory = null;

    @OneToMany(mappedBy = "superCategory", cascade = {CascadeType.ALL})
    private List<Category> subCategories = Collections.emptyList();

    private boolean deleted = false;

    @Id
    private final UUID categoryId = UUID.randomUUID();

}