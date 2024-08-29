package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "sizes")
public class Size {

    private String name;
    private String description;

    private boolean deleted = false;

    @Id
    private UUID sizeId = UUID.randomUUID();

    public Size(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Size() {}

    public UUID getSizeId() {
        return sizeId;
    }

    public void setSizeId(UUID sizeId) {
        this.sizeId = sizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}