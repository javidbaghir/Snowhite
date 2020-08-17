package com.Snowhite.repository;

import com.Snowhite.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAll();

    Product findById(int id);

    Product findByName(String name);

    Product findByIdNotAndName(int id, String name);
}
