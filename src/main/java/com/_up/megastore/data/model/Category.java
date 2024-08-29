package com._up.megastore.data.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "categories")
public class Category {

    private String name;
    private String description;

    @ManyToOne
    private Category superCategory = null;

    @OneToMany(mappedBy = "categoryId", cascade = {CascadeType.ALL})
    private List<Category> subCategories;

    private boolean deleted = false;

    @Id
    private UUID categoryId = UUID.randomUUID();

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category() {}

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

    public Category getSuperCategory() {
        return superCategory;
    }

    public void setSuperCategory(Category superCategory) {
        this.superCategory = superCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}