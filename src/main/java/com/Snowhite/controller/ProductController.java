package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Product;
import com.Snowhite.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("api/v1/products")
@CrossOrigin
public class ProductController {

    static Logger log = LoggerFactory.getLogger(ProductController.class.getName());


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

//    @ApiOperation(value = "Add Product",
//            notes = "This method adds a new product",
//            response = ResponseEntity.class)
//    @PostMapping
//    public ResponseEntity<Product> addProduct(@RequestParam("name") @Valid String  name,
//                                              @RequestParam("file")  MultipartFile file) throws IOException {
//
//        log.debug("Product add post method is called");
//
//        var product =  Product.builder().name(name).build();
//
//        Product addProduct = productService.addProduct(product, file);
//
//        log.info("New product added, product - " + addProduct);
//
//        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid Product product,
                                              MultipartFile file) throws IOException {

        log.debug("Product add post method is called");

        Product addProduct = productService.addProduct(product, file);

        log.info("New product added, product - " + addProduct);

        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
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
    public ResponseEntity<Product> edit(@Valid Product product,
                                        MultipartFile file) throws IOException {

        log.debug("Product edit post method is called");

//        var product = Product.builder().name(name).build();

        Product editProduct = productService.editProduct(product, file);

        log.info("This " + editProduct + "  products has been updated");

     return new ResponseEntity<>(editProduct, HttpStatus.OK);
    }

}
