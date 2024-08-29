package com._up.megastore.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "states")
public class State {

    private String name;
    private String description;

    private boolean deleted = false;

    @Id
    private UUID stateId = UUID.randomUUID();

    public State(String name, String description) {
        this.name = name;
        this.description = description;
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