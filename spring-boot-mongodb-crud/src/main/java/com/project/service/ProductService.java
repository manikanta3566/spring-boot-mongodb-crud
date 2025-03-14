package com.project.service;

import com.project.document.Product;
import com.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        if (!StringUtils.hasLength(product.getName())) {
            throw new RuntimeException("product name should not be empty");
        }
        return productRepository.save(product);
    }

    public Product getProductById(String productId){
        return productRepository.findById(productId).get();
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product updateProduct(String productId,Product productUpdate){
        Product existingProduct = productRepository.findById(productId).get();
        existingProduct.setName(productUpdate.getName());
        existingProduct.setPrice(productUpdate.getPrice());
        existingProduct.setCategory(productUpdate.getCategory());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String productId){
        productRepository.deleteById(productId);
    }
}
