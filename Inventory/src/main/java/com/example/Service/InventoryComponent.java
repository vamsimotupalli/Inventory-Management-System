package com.example.Service;

import com.example.CommonDTO.Orders;
import com.example.Entity.Inventory;
import com.example.Entity.InventoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class InventoryComponent {

    private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);
    @Autowired
    public InventoryService inventoryService;

    @Autowired
    public RedisTemplate<Integer,Object> redisTemplate;


    private final KafkaTemplate<Integer,Object> kafkaTemplate;

    public InventoryComponent(KafkaTemplate<Integer, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.placed",concurrency = "1")
    public void handleOnPlacedOrder(Orders orders) {
        int productId = orders.getProductid();
        int orderQty = orders.getOrderQuantity();

        InventoryDTO cachedInventory = (InventoryDTO) redisTemplate.opsForValue().get(productId);
        Inventory inventory;

        if (cachedInventory != null) {
            logger.info("Inventory fetched from Redis for productId: {}", productId);
            inventory = new Inventory(cachedInventory);
        }
        else
        {
            inventory = inventoryService.getProduct(productId);
            if (inventory == null) {
                logger.warn("Inventory not found for productId: {}", productId);
                kafkaTemplate.send("order.failure",orders.getOrderid(),orders);
                return;
            }
            if (inventory.getAvailable() < orderQty) {
                logger.info("Insufficient inventory for productId: {}", productId);
                kafkaTemplate.send("order.failure",orders.getOrderid() ,orders);
                return;
            }
        }
        inventory.setAvailable(inventory.getAvailable() - orderQty);
        inventory.setReserved(inventory.getReserved() + orderQty);
        inventoryService.createInventory(inventory);

        InventoryDTO inventoryDTO = new InventoryDTO(inventory);
        redisTemplate.opsForValue().set(productId, inventoryDTO, Duration.ofMinutes(10));
        logger.info("Inventory fetched from DB and cached for productId: {}", productId);

        kafkaTemplate.send("order.confirmed",orders.getOrderid() ,orders);
    }

    @KafkaListener(topics = "delivery.completed",concurrency = "1")
    public void reduceReservedStockOnDelivery(Orders order) {
        Inventory inventory = inventoryService.getProduct(order.getProductid());

        inventory.setReserved(Math.max(inventory.getReserved() - order.getOrderQuantity(), 0));
        inventoryService.createInventory(inventory);

        // Optional: Update Redis
        redisTemplate.opsForValue().set(order.getProductid(), new InventoryDTO(inventory), Duration.ofMinutes(10));
        kafkaTemplate.send("order.completed",order.getOrderid(),order);

        logger.info("Reserved stock reduced after successful delivery for product ID: {}", order.getProductid());
    }

}
