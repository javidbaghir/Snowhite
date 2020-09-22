package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.GetInventoriesRequest;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.projection.BudgetProjection;
import com.Snowhite.projection.GainProjection;
import com.Snowhite.projection.TotalWeight;
import com.Snowhite.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("api/v1/inventories")
@CrossOrigin
public class InventoryController {

    static Logger log = LoggerFactory.getLogger(InventoryController.class.getName());

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private SnowhiteConfigration snowhiteConfigration;


    @ApiOperation(value = "Get all Inventories",
                  notes = "This method returns all inventories with status and paging",
                  response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<List<Inventory>> getInventories(@RequestBody GetInventoriesRequest request) {

        System.out.println("Request - " + request);

        int pageSize = snowhiteConfigration.getPageSize();

        Pageable pageable = PageRequest.of(request.getPage() - 1, pageSize, Sort.Direction.fromString(request.getSortDirection()),request.getSortColumn());

//        if (status == null) {
//            return new ResponseEntity<>(inventoryService.findAll(pageable), HttpStatus.OK);
//        } else {
            return new ResponseEntity<>(inventoryService.findAll(request.getStatus(),request.getStartDate(),request.getEndDate(), pageable),  HttpStatus.OK);
//        return new ResponseEntity<>(inventoryService.findAllByStatus(status, pageable, filter),  HttpStatus.OK);

    }

    @ApiOperation(value = "Add Inventory",
                  notes = "This method adds a new inventory",
                  response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody @Valid Inventory inventory, BindingResult bindingResult) {

        log.debug("Inventory add post method is called");

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Error");
        }

        Inventory addInventory = inventoryService.addInventory(inventory);

        log.info("New inventory added " + addInventory);

        return new ResponseEntity<>(addInventory, HttpStatus.CREATED);
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

        log.debug("Inventory edit post method is called");

        Inventory updateInventory = inventoryService.updateInventory(inventory);
        log.info("This " + inventory.getId() + " - id inventory has been updated, Inventory - " + updateInventory);

        return new ResponseEntity<>(updateInventory, HttpStatus.OK);

    }

    @ApiOperation(value = "Sale Inventory",
                  notes = "This method converts the status of the inventory to sold",
                  response = ResponseEntity.class)
    @PostMapping("/sale/{id}")
    public ResponseEntity<Inventory> saleInventory(@PathVariable(name = "id") int id,
                                                  @RequestParam(value = "salePrice") Double salePrice) {

        log.debug("Inventory sale post method is called");

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.SOLD);

        inventory.setSalePrice(salePrice);

        Inventory saleInventory = inventoryService.updateInventory(inventory);

        log.info("This " + inventory.getId() + " - id inventory was sold for " + salePrice + " " + saleInventory.getCurrency());

        return new ResponseEntity<>(saleInventory, HttpStatus.OK);
    }

    @ApiOperation(value = "Destroy Inventory",
                  notes = "This method converts the status of the inventory to destroy",
                  response = ResponseEntity.class)
    @PostMapping("/destroy/{id}")
    public ResponseEntity<Inventory> destroyInventory(@PathVariable(name = "id") int id) {

        log.debug("Inventory destroy post method is called");

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.DESTROYED);

        Inventory destroyInventory = inventoryService.updateInventory(inventory);

        log.info("This " + destroyInventory.getId() + " - id inventory was destroyed");


        return new ResponseEntity<>(destroyInventory, HttpStatus.OK);
    }

    @ApiOperation(value = "Return Inventory",
                  notes = "This method converts the status of the inventory to return",
                  response = ResponseEntity.class)
    @PostMapping("/return/{id}")
    public ResponseEntity<Inventory> returnInventory(@PathVariable(name = "id") int id) {

        log.debug("Inventory destroy post method is called");

        Inventory inventory = inventoryService.findById(id);

        inventory.setStatus(Status.RETURN);

        Inventory returnInventory = inventoryService.updateInventory(inventory);

        log.info("This " + returnInventory.getId() + " - id inventory was returned");

        return new ResponseEntity<>(returnInventory, HttpStatus.OK);
    }



    @ApiOperation(value = "Sale between dates",
                  notes = "This method returns inventories sold between dates",
                  response = List.class)
    @GetMapping("/inventory/date")
    public List<Inventory> getInventoryByDate (
            @RequestParam(value = "startDate") Date startDate,
            @RequestParam(value = "endDate") Date endDate) {

        return inventoryService.getInventoryByDate(startDate, endDate);
    }

    @ApiOperation(value = "Gains between dates",
            notes = "This method returns gains between dates with currency",
            response = GainProjection.class)
    @GetMapping("/gains")
    public List<GainProjection> getGainsByDate(@RequestParam(value = "startDate") Date startDate,
                                               @RequestParam(value = "endDate") Date endDate) {

        log.debug("Inventory gains by date get method is called");

        log.info("He looked at his earnings between " + startDate + " - " + endDate);

        return inventoryService.getGainsByDate(startDate, endDate);
    }


    @ApiOperation(value = "General budget",
                  notes = "This method returns all my budget with currency",
                  response = BudgetProjection.class)
    @GetMapping("/generalBudget")
    public List<BudgetProjection> getGeneralBudget() {

        log.debug("Inventory get general budget get method is called");


        log.info("He looked at his budget");

        return inventoryService.getGeneralBudget();
    }


    @ApiOperation(value = "Total weight",
            notes = "This method returns all my weight with unite",
            response = TotalWeight.class)
    @GetMapping("/totalWeight")
    public List<TotalWeight> getTotalWeight() {

        log.debug("Inventory get total weight get method is called");

        log.info("He looked at his total weight");

        return inventoryService.getTotalWeight();
    }


}
