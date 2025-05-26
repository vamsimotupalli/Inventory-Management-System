package com.example.Inventory.Order.Management.System.Service;

import com.example.Inventory.Order.Management.System.Entity.Orders;
import com.example.Inventory.Order.Management.System.Repository.OrderRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    public void createOrder(Orders order)
    {
        orderRepository.save(order);
    }

}
