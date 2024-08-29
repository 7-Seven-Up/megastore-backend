package com._up.megastore.data.model;

import com._up.megastore.data.enums.Role;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
public class User {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Address> addresses = Collections.emptyList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Order> orders = Collections.emptyList();

    private boolean deleted = false;

    @Id
    private UUID userId = UUID.randomUUID();

    public User(String username, String password, String fullName, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}