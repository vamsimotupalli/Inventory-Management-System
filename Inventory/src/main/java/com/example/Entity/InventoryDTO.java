package com.example.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InventoryDTO implements Serializable {
    private int id;
    private String productName;
    private int available;
    private int reserved;
    private LocalDateTime updatedAt;

    public InventoryDTO(Inventory inventory) {
        this.id = inventory.getId();
        this.productName = inventory.getProductName();
        this.available = inventory.getAvailable();
        this.reserved = inventory.getReserved();
        this.updatedAt = inventory.getUpdatedAt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
