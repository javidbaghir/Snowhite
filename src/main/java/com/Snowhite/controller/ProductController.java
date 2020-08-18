package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Product;
import com.Snowhite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    private SnowhiteConfigration snowhiteConfigration;

    @GetMapping
    public ResponseEntity<List<Product>> index(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product,
                              MultipartFile file) throws IOException {

            return new ResponseEntity<>(productService.addProduct(product, file), HttpStatus.OK);
        }

    @GetMapping("/edit/{id}")
    public Product edit(@PathVariable int id) {

        return productService.findById(id);
    }

    @PutMapping("/edit")
    public Product edit(@RequestBody @Valid Product product,
                        MultipartFile file) throws IOException{

            return productService.addProduct(product, file);
    }

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam(name = "id") int id) {

        Product product = productService.findById(id);

        if (product == null) {
            throw new RuntimeException("");
        } else {
             productService.deleteById(id);
        }
    }

}
