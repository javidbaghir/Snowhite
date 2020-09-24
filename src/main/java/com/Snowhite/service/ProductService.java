package com.Snowhite.service;

import com.Snowhite.domain.Product;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<Product> findAll(Pageable pageable, String filter);

    Product findById(long id);

    Product findByName(String name);

    Product findByIdNotAndName(long id, String name);

    Product addProduct(Product product, MultipartFile file) throws IOException;

    Product updateProduct(Product product, MultipartFile file) throws IOException;

}
