package com.example.Inventory.Order.Management.System.Repository;

import com.example.Inventory.Order.Management.System.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
}
