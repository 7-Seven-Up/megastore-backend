package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "products")
public class Product {

    private String name;
    private String description;
    private double price;
    private String imageURL;
    private int stock;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Product variantOf = null;

    private String color = "#ffffff";
    private boolean deleted = false;

    @OneToMany(mappedBy = "variantOf")
    List<Product> variants = Collections.emptyList();

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails = Collections.emptyList();

    @Id
    private UUID productId = UUID.randomUUID();

    public Product(String name, String description, Double price, String imageURL, int stock, Size size) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.stock = stock;
        this.size = size;
    }

    public Product() {}

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Product getVariantOf() {
        return variantOf;
    }

    public void setVariantOf(Product variantOf) {
        this.variantOf = variantOf;
    }

    public List<Product> getVariants() {
        return variants;
    }

    public void setVariants(List<Product> variants) {
        this.variants = variants;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}