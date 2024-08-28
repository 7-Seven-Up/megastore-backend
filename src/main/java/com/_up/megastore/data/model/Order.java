package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "orders")
public class Order {

    private int number;
    private double total;

    @ManyToOne
    private User user;

    @ManyToOne
    private State state;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = Collections.emptyList();

    private LocalDate date = LocalDate.now();

    @Id
    private UUID orderId = UUID.randomUUID();

    public Order(int number, Double total, User user, State state) {
        this.number = number;
        this.total = total;
        this.user = user;
        this.state = state;
    }

    public Order() {}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

}