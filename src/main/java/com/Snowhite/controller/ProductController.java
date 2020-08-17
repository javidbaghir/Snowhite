package com.Snowhite.controller;

import com.Snowhite.config.SnowhiteConfigration;
import com.Snowhite.domain.Product;
import com.Snowhite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private SnowhiteConfigration snowhiteConfigration;

    @GetMapping
    public List<Product> index(){
        return productService.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product,
                              MultipartFile file) throws IOException {

        System.out.println("1");

        boolean fileOK = false;
        System.out.println("2");
        byte[] bytes = file.getBytes();
        System.out.println("3");
        String fileName = file.getOriginalFilename();
        System.out.println("4");
        Path path = Paths.get("src/main/resources/static/media/" + fileName);

        if (fileName.endsWith("jpg") || fileName.endsWith("png") ) {
            fileOK = true;
        }

        Product nameExist = productService.findByName(product.getName());

        if (! fileOK) {

            throw new RuntimeException("");

        } else if (nameExist != null) {

            throw new RuntimeException("");

        } else {

            product.setImage(fileName);

           Product saveProduct =  productService.addProduct(product);

            Files.write(path, bytes);

            return saveProduct;
        }
    }

    @GetMapping("/edit/{id}")
    public Product edit(@PathVariable int id) {
        return productService.findById(id);
    }

    @PutMapping("/edit")
    public Product edit(@RequestBody Product product,
                        MultipartFile file) throws IOException {

        Product currentProduct = productService.findById(product.getId());

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + fileName);


        if (!file.isEmpty()) {
            if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
                fileOK = true;
            }
        } else {
            fileOK = true;
        }

        Product productExist = productService.findByIdNotAndName(product.getId(), product.getName());

        if (!fileOK) {

            throw new RuntimeException("");

        } else if (productExist != null) {

            throw new RuntimeException("");

        } else {

            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
                Files.delete(path2);
                product.setImage(fileName);
                Files.write(path, bytes);

            } else {
                product.setImage(currentProduct.getImage());
            }

            return productService.addProduct(product);
        }
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
