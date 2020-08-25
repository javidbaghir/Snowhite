package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SnowhiteConfigration snowhiteConfigration;

    @GetMapping
    public ResponseEntity<Page<Inventory>> getInventories(@RequestParam(value = "status", required = false) Status status,
                                             @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                             @RequestParam(value = "sortColumn",  defaultValue = "id", required = false) String sortColumn,
                                             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                             @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
        int pageSize = snowhiteConfigration.getPageSize();

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.fromString(sortDirection), sortColumn);
//        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return new ResponseEntity<>(inventoryService.findAllByStatus(status, pageable, filter),  HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody @Valid Inventory inventory, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Error");
        }

        System.out.println("Controller add");

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.CREATED);
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
