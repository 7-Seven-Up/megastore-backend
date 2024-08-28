package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity(name = "states")
public class State {

    private String name;
    private String description;

    @OneToMany(mappedBy = "state")
    private List<Order> orders;

    private boolean deleted = false;

    @Id
    private UUID stateId = UUID.randomUUID();

    public State(String name, String description, List<Order> orders) {
        this.name = name;
        this.description = description;
        this.orders = orders;
    }

    public State() {}

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public UUID getStateId() {
        return stateId;
    }

    public void setStateId(UUID stateId) {
        this.stateId = stateId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}