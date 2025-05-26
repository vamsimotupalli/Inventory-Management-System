package com.example.Inventory.Order.Management.System.Controller;

import com.example.Inventory.Order.Management.System.Entity.Orders;
import com.example.Inventory.Order.Management.System.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping("/create")
    public void CreateOrder(@RequestBody Orders orders)
    {
        orderService.createOrder(orders);
    }

}
