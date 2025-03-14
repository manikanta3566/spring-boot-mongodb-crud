package com.project.controller;

import com.project.document.Product;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.findAllProducts(),HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId){
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }

    @PutMapping("{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable String productId,@RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(productId,product),HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable String productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
    }
}
