package com.example.Controller;

import com.example.Entity.Inventory;
import com.example.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    public InventoryService inventoryService;

    @PostMapping("/createInventory")
    public ResponseEntity<Inventory> create (@RequestBody Inventory inventory)
    {
        Inventory saved = inventoryService.createInventory((inventory));
        return ResponseEntity.ok(saved);
    }
}
