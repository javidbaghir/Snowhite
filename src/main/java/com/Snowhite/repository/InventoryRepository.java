package com.Snowhite.repository;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

//    Page<Inventory> findAllByStatus(Status status, Pageable pageable, String filter);

    Page<Inventory> findAllByStatus(Status status, Pageable pageable);

    Page<Inventory> findAll(Pageable pageable);

    Inventory findById(int id);

    long count();

    Inventory findByInventoryNumber(long number);

    @Query(nativeQuery = true, value = "SELECT i from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date")
    List<Inventory> findDateRange(@Param("start_date") Date startDate, @Param("end_date") Date endDate);


}
