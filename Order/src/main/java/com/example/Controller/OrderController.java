package com.example.Controller;

import com.example.CommonDTO.Orders;
import com.example.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Orders> CreateOrder(@RequestBody Orders orders)
    {
        orderService.createOrder(orders);
        return ResponseEntity.ok(orders);
    }

}
