package com.Snowhite.repository;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

//    @Query(nativeQuery = true, value = "SELECT i FROM inventory i where i.status = :status")
    Page<Inventory> findAllByStatus(Status status, Pageable pageable);
    List<Inventory> findAllByStatus(Status status);

    Inventory findById(int id);


}
