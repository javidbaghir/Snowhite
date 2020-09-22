package com.Snowhite.repository;

import com.Snowhite.domain.Currency;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.domain.WeightUnite;
import com.Snowhite.projection.BudgetProjection;
import com.Snowhite.projection.GainProjection;
import com.Snowhite.projection.TotalWeight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>, JpaSpecificationExecutor<Inventory> {

//    Page<Inventory> findAllByStatus(Status status, Pageable pageable);
    Page<Inventory> findAllByStatus(Status status, Date startDate, Date endDate, Pageable pageable);



    Page<Inventory> findAll(Specification<Inventory> specification, Pageable pageable);

//    Page<Inventory> findAll(Pageable pageable);

    Inventory findById(int id);

    Inventory findByInventoryNumber(long number);

    @Query(nativeQuery = true, value = "SELECT inventory i SET i.inventory_number = :inventory_number, i.karat = :karat, i.weight = :weight, " +
            "i.weight_unite = :weight_unite, i.prob = :prob, i.cost = :cost, i.sale_price = :sale_price, i.currency = :currency, i.product_id = :product_id," +
            "i.status = :status, i.date_of_addition = :date_of_addition")
    Inventory updateInventory(@Param("inventory_number") long inventoryNumber, @Param("karat") double karat, @Param("weight") double weight,
                              @Param("weight_unite") WeightUnite weightUnite, @Param("prob") int prob, @Param("cost") double cost,
                              @Param("sale_price") double sale_price, @Param("currency") Currency currency, @Param("product_id") int productId,
                              @Param("status") Status status, @Param("date_of_addition") Date dateOfAddition);


//    @Query(nativeQuery = true, value = "select i.id, i.inventory_number, i.karat, i.weight, i.weight_unite, i.prob, i.cost, i.sale_price, i.currency, i.product_id, i.status, i.created_at, i.updated_at from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date")
    @Query(nativeQuery = true, value = "select sum(i.sale_price - i.cost) as qazanc, i.id, i.inventory_number, i.karat, i.weight, i.weight_unite, i.prob, i.cost, i.sale_price, i.currency, i.product_id, i.status, i.created_at, i.updated_at " +
            "from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date\n" +
            "group by i.id, i.inventory_number, i.karat, i.weight, i.weight_unite, i.prob, i.cost, i.sale_price, i.currency, i.product_id, i.status")
    List<Inventory> getInventoryByDate(@Param("start_date") Date startDate, @Param("end_date") Date endDate);

    @Query(nativeQuery = true, value = "SELECT sum(i.cost) as cost, i.currency as currency FROM inventory i WHERE i.status = 'AVAILABLE' group by i.currency")
    List<BudgetProjection> getGeneralBudget();


    @Query(nativeQuery = true, value = "select sum(i.sale_price - i.cost) as gains, i.currency as currency from inventory i where i.status = 'SOLD' and i.updated_at BETWEEN :start_date AND :end_date group by i.currency")
    List<GainProjection> getGainsByDate(@Param("start_date") Date startDate, @Param("end_date") Date endDate);

    @Query(nativeQuery = true, value = "select sum(i.weight) as weight, i.weight_unite as weightUnite from inventory i where i.status = 'AVAILABLE' group by i.weight_unite")
    List<TotalWeight> getTotalWeight();
}
