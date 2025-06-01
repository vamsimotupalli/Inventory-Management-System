package com.example.Service;

import com.example.Entity.Inventory;
import com.example.Repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    public InventoryRepository inventoryRepository;

    public Inventory createInventory(Inventory inventory)
    {
        inventoryRepository.save(inventory);
        return inventory;
    }

    public  Inventory getProduct(int productid)
    {
        return inventoryRepository.findById(productid)
                .orElseThrow(() -> new RuntimeException("Product not Found"));

    }







}
