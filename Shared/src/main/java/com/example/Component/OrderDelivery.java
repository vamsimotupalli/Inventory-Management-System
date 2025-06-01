package com.example.Component;

import com.example.CommonDTO.OrderStatus;
import com.example.CommonDTO.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderDelivery {

    private static final Logger logger = LoggerFactory.getLogger(OrderDelivery.class);

    private final KafkaTemplate<Integer, Orders> kafkaTemplate;

    public OrderDelivery(KafkaTemplate<Integer, Orders> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.created",concurrency = "1")
    public void handleOrderDelivery(Orders orders) {
        if(orders.getOrderStatus() == OrderStatus.PROCESSING)
        {
            boolean deliverySuccess = deliver(orders);

            if (deliverySuccess) {
                logger.info("Delivery successful for order ID: {}", orders.getOrderid());
                kafkaTemplate.send("delivery.completed", orders.getOrderid(),orders);
            } else {
                logger.warn("Delivery failed for order ID: {}", orders.getOrderid());
                kafkaTemplate.send("order.failure",orders.getOrderid(),orders);
            }
        }

    }

    public boolean deliver(Orders order) {
        // Simulate a delivery with 90% success rate
        return Math.random() > 0.1;
    }
}
