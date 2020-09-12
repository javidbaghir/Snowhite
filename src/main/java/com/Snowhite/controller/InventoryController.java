package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SnowhiteConfigration snowhiteConfigration;

    @ApiOperation(value = "Get all Inventories",
                  notes = "This method returns all inventories with status and paging",
                  response = ResponseEntity.class)
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

    @ApiOperation(value = "Add Inventory",
                  notes = "This method adds a new inventory",
                  response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody @Valid Inventory inventory, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Error");
        }

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Inventory get mapping",
                  notes = "This method returns edit method according to id",
                  response = Inventory.class)
    @GetMapping("/edit/{id}")
    public Inventory updateInventory(@PathVariable(name = "id") int id) {

        return inventoryService.findById(id);
    }

    @ApiOperation(value = "Update Inventory post mapping",
                  notes = "This method update inventory",
                  response = ResponseEntity.class)
    @PostMapping("/edit")
    public ResponseEntity<Inventory> updateInventory(@RequestBody @Valid Inventory inventory) {

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);

    }

    @ApiOperation(value = "Sale Inventory",
                  notes = "This method converts the status of the inventory to sold",
                  response = ResponseEntity.class)
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

    @ApiOperation(value = "Destroy Inventory",
                  notes = "This method converts the status of the inventory to destroy",
                  response = ResponseEntity.class)
    @PostMapping("/destroy/{id}")
    public ResponseEntity<Inventory> destroyInventory(@PathVariable(name = "id") int id) {

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.DESTROYED);

        return new ResponseEntity<>(inventoryService.addInventory(inventory), HttpStatus.OK);
    }

    @ApiOperation(value = "Return Inventory",
                  notes = "This method converts the status of the inventory to return",
                  response = ResponseEntity.class)
    @PostMapping("/return/{id}")
    public ResponseEntity<Inventory> returnInventory(@PathVariable(name = "id") int id) {
        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.RETURN);

        System.out.println("Inventory " + inventory);

        return new ResponseEntity<>(inventoryService.editInvenotory(inventory), HttpStatus.OK);
    }

    @ApiOperation(value = "Number of inventories",
                  notes = "This method returns all number of inventories",
                  response = Long.class)
    @GetMapping("/countInventories")
    public Long inventoriesCount() {
        return inventoryService.count();
    }



    @ApiOperation(value = "Sale between dates",
                  notes = "This method returns inventories sold between dates",
                  response = List.class)
    @GetMapping("/inventory/date")
    public Optional<Inventory> getInventoryByDate (
            @RequestParam(value = "startDate") Date startDate,
            @RequestParam(value = "endDate") Date endDate) {

        return inventoryService.getInventoryByDate(startDate, endDate);
    }

    @ApiOperation(value = "Gains between dates",
            notes = "This method returns gains between dates",
            response = Long.class)
    @GetMapping("/gains")
    public Long getGainsByDate(@RequestParam(value = "startDate") Date startDate,
                @RequestParam(value = "endDate") Date endDate) {

        return inventoryService.getGainsByDate(startDate, endDate);
    }


    @ApiOperation(value = "General budget",
                  notes = "This method returns all my budget",
                  response = Long.class)
    @GetMapping("/generalBudget")
    public Long getGeneralBudget() {
        return inventoryService.getGeneralBudget();
    }


}
