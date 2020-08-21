package com.Snowhite.repository;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findAllByStatus(Status status);
//    List<Inventory> findAllByStatus(int status);

    Inventory findById(int id);

}
