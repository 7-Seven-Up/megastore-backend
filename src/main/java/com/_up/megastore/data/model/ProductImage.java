package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "productImages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductImage {

    @Id
    private String imageURL;

}