package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Product;
import com.Snowhite.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "Get all Products",
            notes = "This method returns all products with paging",
            response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "sortColumn", defaultValue = "id", required = false) String sortColumn,
                                                     @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                     @RequestParam(value = "filter", defaultValue = "", required = false) String nameFilter
    ) {

        int pageSize = snowhiteConfigration.getPageSize();

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.fromString(sortDirection), sortColumn);

        return new ResponseEntity<>(productService.findAll(pageable, nameFilter), HttpStatus.OK);
    }

    @ApiOperation(value = "Add Product",
            notes = "This method adds a new product",
            response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestParam("name") @Valid String  name,
                                              @RequestParam("file")  MultipartFile file) throws IOException {

            var product =  Product.builder().name(name).build();
            return new ResponseEntity<>(productService.addProduct(product, file), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Product get mapping",
            notes = "This method returns edit method according to id",
            response = Product.class)
    @GetMapping("/edit/{id}")
    public Product edit(@PathVariable(name = "id") int id) {

        return productService.findById(id);
    }

    @ApiOperation(value = "Update Product post mapping",
            notes = "This method update product",
            response = ResponseEntity.class)
    @PostMapping("/edit")
    public ResponseEntity<Product> edit(@RequestParam("name") @Valid String  name,
                                        @RequestParam("file")  MultipartFile file) throws IOException {

        System.out.println("Name " + name);
        System.out.println("File " + file);

        var product = Product.builder().name(name).build();

     return new ResponseEntity<>(productService.editProduct(product, file), HttpStatus.OK);
    }



}
