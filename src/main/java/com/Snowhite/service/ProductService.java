package com.Snowhite.service;

import com.Snowhite.domain.Product;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    Product findByName(String name);

    Product findByIdNotAndName(int id, String name);

    Product addProduct(Product product);

    void deleteById(int id);
}
