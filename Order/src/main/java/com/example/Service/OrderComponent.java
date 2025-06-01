package com.example.Service;

import com.example.CommonDTO.OrderStatus;
import com.example.CommonDTO.Orders;
import com.example.Repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderComponent {

    private static final Logger logger = LoggerFactory.getLogger(OrderComponent.class);
    private final KafkaTemplate<Integer,Orders> kafkaTemplate;
    @Autowired
    private OrderService orderService;

    public OrderComponent(KafkaTemplate<Integer, Orders> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.confirmed",concurrency = "1")
    public void handleOnOrderSuccess(Orders orders)
    {
        logger.info("Order Status Changed to PROCESSING");
        Orders updatedOrder = orderService.updateOrder(orders,OrderStatus.PROCESSING);
        kafkaTemplate.send("order.created",orders.getOrderid(),updatedOrder);
    }
    @KafkaListener(topics = "order.failure",concurrency = "1")
    public void handleOnOrderFailure(Orders orders)
    {
        Orders updatedOrder = orderService.updateOrder(orders,OrderStatus.CANCELLED);
        logger.info("Order Status Changed to CANCELLED");
    }
    @KafkaListener(topics = "order.completed",concurrency = "1")
    public void handleOnOrderCompleted(Orders orders)
    {
        Orders updatedOrder = orderService.updateOrder(orders,OrderStatus.COMPLETED);
        logger.info("Order Status Changed to COMPLETED");
    }
}
