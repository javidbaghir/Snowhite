package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Product;
import com.Snowhite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SnowhiteConfigration snowhiteConfigration;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "sortColumn", defaultValue = "1", required = false) String sortColumn,
                                          @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                          @RequestParam(value = "filter", defaultValue = "sar", required = false) String nameFilter) {

        int pageSize = snowhiteConfigration.getPageSize();

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.fromString(sortDirection), sortColumn);

        return new ResponseEntity<>(productService.findAll(pageable, nameFilter), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestParam("name") @Valid String  name,
                                              @RequestParam("file")  MultipartFile file) throws IOException {

        var product =  Product.builder().name(name).build();
            return new ResponseEntity<>(productService.addProduct(product, file), HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    public Product edit(@PathVariable int id) {

        return productService.findById(id);
    }

    @PostMapping("/edit")
    public Product edit(@RequestBody @Valid Product product,
                        MultipartFile file) throws IOException {

            return productService.editProduct(product, file);
    }

}
