package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity(name = "addresses")
public class Address {

    private String street;
    private String department;
    private int number;

    @ManyToOne
    private User user;

    private boolean deleted = false;

    @Id
    private UUID addressId = UUID.randomUUID();

    public Address(String street, String department, int number, User user) {
        this.street = street;
        this.department = department;
        this.number = number;
        this.user = user;
    }

    public Address() {}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}