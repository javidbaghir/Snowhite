package com.Snowhite.service.impl;

import com.Snowhite.domain.Product;
import com.Snowhite.exception.FileOkException;
import com.Snowhite.exception.NoDataFoundException;
import com.Snowhite.exception.ProductExistException;
import com.Snowhite.exception.ProductNotFoundException;
import com.Snowhite.repository.ProductRepository;
import com.Snowhite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable, String filter) {
        Page<Product> products = productRepository.findAll(pageable, "%" + filter + "%");

        if (products.isEmpty()) {
            throw new NoDataFoundException();
        }

        return products;
    }

    @Override
    public Product findById(long id) {

        Product product = productRepository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        } else {

            return productRepository.findById(id);
        }
    }


    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product findByIdNotAndName(long id, String name) {
        return productRepository.findByIdNotAndName(id, name);
    }

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Override
    public Product addProduct(Product product, MultipartFile file) throws IOException {

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + fileName);

        if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
            fileOK = true;
        }

        Product nameExist = productRepository.findByName(product.getName());

        if (!fileOK) {
            throw new FileOkException();

        } else if (nameExist != null) {

            throw new ProductExistException();

        } else {

            product.setImage(fileName);

            Product saveProduct = productRepository.save(product);

            Files.write(path, bytes);

            return saveProduct;
        }
    }


    @Modifying
    @Override
    public Product updateProduct(Product product, MultipartFile file) throws IOException {

        Product currentProduct = productRepository.findById(product.getId());

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

        Product productExist = productRepository.findByIdNotAndName(product.getId(), product.getName());

        if (!fileOK) {

            throw new FileOkException();

        } else if (productExist != null) {

            throw new ProductExistException();

        } else {

            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
                Files.delete(path2);
                product.setImage(fileName);
                Files.write(path, bytes);

            } else {
                product.setImage(currentProduct.getImage());
            }
//            return productRepository.save(product);
            return productRepository.updateProduct(product.getId(), product.getName(), file);
        }
    }

}
