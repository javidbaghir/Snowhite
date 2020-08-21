package com.Snowhite.controller;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.exception.InventoryNotFoundException;
import com.Snowhite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> index(@RequestParam("status") String status) {

        return new ResponseEntity<>(inventoryService.findAllByStatus(Status.fromValue(status)),  HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody @Valid Inventory inventory) {

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public Inventory updateInventory(@PathVariable(name = "id") int id) {

        return inventoryService.findById(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<Inventory> updateInventory(@RequestBody @Valid Inventory inventory) {

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);

    }

}
