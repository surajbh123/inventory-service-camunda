package com.example.loanapplication.inventory;

import java.time.OffsetDateTime;

public class Inventory {

    private Long id;
    private String name;
    private Integer quantity;
    private Long parentId;
    private OffsetDateTime updatedAt;

    public Inventory() {}

    public Inventory(Long id, String name, Integer quantity, Long parentId, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.parentId = parentId;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
