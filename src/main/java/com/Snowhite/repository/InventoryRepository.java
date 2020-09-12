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
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Page<Inventory> findAllByStatus(Status status, Pageable pageable);

    Page<Inventory> findAll(Pageable pageable);

    Inventory findById(int id);

    long count();

    Inventory findByInventoryNumber(long number);

    @Query(nativeQuery = true, value = "SELECT i.id, i.inventory_number, i.karat, i.weight, i.weight_unite, i.prob, i.cost, i.sale_price, i.product_id, i.status, i.created_at, i.updated_at from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date")
    Optional<Inventory> getInventoryByDate(@Param("start_date") Date startDate, @Param("end_date") Date endDate);

    @Query(nativeQuery = true, value = "SELECT sum(i.cost) FROM inventory i WHERE i.status = 'AVAILABLE'")
    Long getGeneralBudget();

    @Query(nativeQuery = true, value = "select sum(i.sale_price - i.cost) from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date")
    Long getGainsByDate(@Param("start_date") Date startDate, @Param("end_date") Date endDate);

}
