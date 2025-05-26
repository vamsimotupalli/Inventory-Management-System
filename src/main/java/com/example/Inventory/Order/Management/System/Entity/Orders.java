package com.example.Inventory.Order.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderId;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    private double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}


