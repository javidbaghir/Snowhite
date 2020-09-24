package com.Snowhite.repository;

import com.Snowhite.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select p from products p where CONCAT (p.name) like :filter")
    Page<Product> findAll(Pageable pageable, @Param("filter") String filter);

    Product findById(long id);

    Product findByName(String name);

    Product findByIdNotAndName(long id, String name);

    @Query(nativeQuery = true, value = "UPDATE products p SET p.name = :productName, p.image = :image WHERE p.id = :id")
    Product updateProduct(@Param("id") long id, @Param("productName") String name, @Param("image") MultipartFile image);

}
