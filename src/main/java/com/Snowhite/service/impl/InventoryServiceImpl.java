package com.Snowhite.service.impl;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.projection.BudgetProjection;
import com.Snowhite.exception.InventoryNotFoundException;
import com.Snowhite.exception.InventoryNumberAlreadyExistException;
import com.Snowhite.exception.NoDataFoundException;
import com.Snowhite.exception.NoGainsThisMonth;
import com.Snowhite.projection.GainProjection;
import com.Snowhite.projection.TotalWeight;
import com.Snowhite.repository.InventoryRepository;
import com.Snowhite.repository.InventorySpesificationImpl;
import com.Snowhite.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventorySpesificationImpl inventorySpesification;

    @Override
    public List<Inventory> findAll(Status status, Date startDate, Date endDate, Pageable pageable){

        List<Inventory> inventories = inventorySpesification.findByCriteria(status, startDate, endDate, pageable);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        }

        return inventories;
    }



    @Override
    public Page<Inventory> findAll(Pageable pageable) {

        Page<Inventory> inventories = inventoryRepository.findAll(pageable);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        }
        return inventories;
    }

    //    @Override
//    public Page<Inventory> findAllByStatus(Status status, Pageable pageable, String filter) {
//
//        Page<Inventory> inventories = inventoryRepository.findAllByStatus(status, pageable, "%" + filter + "%");
//
//        if (inventories.isEmpty()) {
//            throw new NoDataFoundException();
//        }
//
//        return inventories;
//    }

    @Override
    public Inventory addInventory(Inventory inventory) {

        Inventory number = inventoryRepository.findByInventoryNumber(inventory.getInventoryNumber());

        if (number != null) {
            throw new InventoryNumberAlreadyExistException();
        } else {
            return inventoryRepository.save(inventory);
        }
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    //    @Override
//    public Inventory updateInventory(Inventory inventory) {
//
//        return inventoryRepository.updateInventory(inventory.getInventoryNumber(), inventory.getKarat(), inventory.getWeight(), inventory.getWeightUnite(),
//                inventory.getProb(), inventory.getCost(), inventory.getSalePrice(), inventory.getCurrency(), inventory.getProduct().getId(),
//                inventory.getStatus(), inventory.getDateOfAddition());
//    }

    @Override
    public Inventory findById(int id) {

        Inventory inventory = inventoryRepository.findById(id);

        if (inventory == null) {
            throw new InventoryNotFoundException(id);
        }

        return inventory;
    }

    @Override
    public List<Inventory> getInventoryByDate(Date startDate, Date endDate) {

        List<Inventory> inventories = inventoryRepository.getInventoryByDate(startDate, endDate);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        } else {
            return inventories;
        }
    }

    @Override
    public List<GainProjection> getGainsByDate(Date startDate, Date endDate) {

        List<GainProjection> getGainsByDate = inventoryRepository.getGainsByDate(startDate, endDate);


        if (getGainsByDate.isEmpty()) {
            throw new NoGainsThisMonth();
        } else {
            return getGainsByDate;
        }
    }

    @Override
    public List<BudgetProjection> getGeneralBudget() {
        return inventoryRepository.getGeneralBudget();
    }

    @Override
    public List<TotalWeight> getTotalWeight() {
        return inventoryRepository.getTotalWeight();
    }
}
