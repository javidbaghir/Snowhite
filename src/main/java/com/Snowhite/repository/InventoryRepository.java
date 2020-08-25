package com.Snowhite.repository;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Integer> {

//    @Query(nativeQuery = true, value = "SELECT i FROM inventory i where i.status = :status")
//    @Query(value = "select p from products p where CONCAT (p.name) like :filter")
    Page<Inventory> findAllByStatus(Status status, Pageable pageable, String filter);
//    List<Inventory> findAllByStatus(Status status);

    Inventory findById(int id);


}
