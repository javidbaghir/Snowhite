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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;
import javax.validation.Valid;
import java.text.DateFormat;
import java.util.Date;
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
                                             @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                             @RequestParam(value = "sortColumn",  defaultValue = "id", required = false) String sortColumn,
                                             @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
//                                             @RequestParam(value = "filter", defaultValue = "", required = false) String filter
    ) {

        int pageSize = snowhiteConfigration.getPageSize();

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.fromString(sortDirection), sortColumn);

        if (status == null) {
            return new ResponseEntity<>(inventoryService.findAll(pageable), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(inventoryService.findAllByStatus(status, pageable),  HttpStatus.OK);
        }

//        return new ResponseEntity<>(inventoryService.findAllByStatus(status, pageable, filter),  HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody @Valid Inventory inventory, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Error");
        }

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.CREATED);
    }

    @GetMapping("/edit/{id}")
    public Inventory updateInventory(@PathVariable(name = "id") int id) {

        return inventoryService.findById(id);
    }

    @PostMapping("/edit")
    public ResponseEntity<Inventory> updateInventory(@RequestBody @Valid Inventory inventory) {

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);

    }

    @PostMapping("/sale/{id}")
    public ResponseEntity<Inventory> saleInventory(@PathVariable(name = "id") int id,
                                                  @RequestParam(value = "salePrice") Double salePrice) {

        System.out.println("Id " + id);

        System.out.println("Sale price " + salePrice);

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.SOLD);

        inventory.setSalePrice(salePrice);

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);
    }

    @PostMapping("/destroy/{id}")
    public ResponseEntity<Inventory> destroyInventory(@PathVariable(name = "id") int id) {

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.DESTROYED);

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);
    }

    @GetMapping("/countInventories")
    public Long inventoriesCount() {
        return inventoryService.count();
    }

    @GetMapping("/date/range")
    public List<Inventory> findDateRange(
            @RequestParam(value = "startDate") Date startDate,
            @RequestParam(value = "endDate") Date endDate
//            Date startDate,
//            Date endDate
        ){


        return inventoryService.findDateRange(startDate, endDate);
    }
}
