package com.example.Service;

import com.example.CommonDTO.OrderStatus;
import com.example.CommonDTO.Orders;
import com.example.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final KafkaTemplate<Integer,Orders> kafkaTemplate;

    @Autowired
    public OrderRepository orderRepository;

    public OrderService(KafkaTemplate<Integer, Orders> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder(Orders order)
    {
        orderRepository.save(order);
        logger.info("Order Saved in Orders Table");
        kafkaTemplate.send("order.placed",order.getOrderid(),order);
    }

    public Orders updateOrder(Orders orders,OrderStatus orderStatus)
    {
        Orders existingOrder = orderRepository.findById(orders.getOrderid()).orElseThrow(()->new RuntimeException("Product not Found"));
        if (existingOrder.getOrderStatus() == orderStatus) {
            logger.info("Order {} already has status {}. No update performed.", existingOrder.getOrderid(), orderStatus);
            return existingOrder;
        }
        existingOrder.setOrderStatus(orderStatus);
        orderRepository.save(existingOrder);
        return existingOrder;
    }

}
