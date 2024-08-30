package com._up.megastore.controllers.responses;

import java.util.List;
import java.util.UUID;

public class ProductResponse {
    private String name;
    private String description;
    private Double price;
    private String imageURL;
    private String color;
    private UUID sizeId;
    private boolean deleted;
    private int stock;
    private List<ProductResponse> variants ;
    private UUID productId;    


    public ProductResponse(String name, String description, Double price, String imageURL, String color, UUID sizeId, boolean deleted, int stock, List<ProductResponse> variants, UUID productId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.color = color;
        this.sizeId = sizeId;
        this.deleted = deleted;
        this.stock = stock;
        this.variants = variants;
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


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public UUID getSizeId() {
        return sizeId;
    }


    public void setSizeId(UUID sizeId) {
        this.sizeId = sizeId;
    }


    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }


    public List<ProductResponse> getVariants() {
        return variants;
    }


    public void setVariants(List<ProductResponse> variants) {
        this.variants = variants;
    }


    public UUID getProductId() {
        return productId;
    }


    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    
    
}