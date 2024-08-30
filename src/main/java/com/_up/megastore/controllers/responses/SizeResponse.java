package com._up.megastore.controllers.responses;

import java.util.UUID;

public class SizeResponse {
    private String name;
    private boolean deleted;
    private UUID sizeId;
    public SizeResponse(String name, boolean deleted, UUID sizeId) {
        this.name = name;
        this.deleted = deleted;
        this.sizeId = sizeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public UUID getSizeId() {
        return sizeId;
    }
    public void setSizeId(UUID sizeId) {
        this.sizeId = sizeId;
    }


}
