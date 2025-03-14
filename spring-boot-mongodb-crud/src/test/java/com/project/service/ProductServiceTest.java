package com.project.service;

import com.project.document.Product;
import com.project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private static Product product=null;

    @BeforeAll
    static void init() {
        product=new Product();
        product.setName("iphone13");
        product.setPrice(50000);
        product.setCategory("electronics");
    }

    @Test
    void create() {
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product dbProduct = productService.create(product);

        assertEquals(product.getId(),dbProduct.getId());
    }

    @Test
    void getProductById(){
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Product dbProduct = productService.getProductById(product.getId());

        assertEquals(dbProduct.getId(),product.getId());
    }

    @Test
    void getAllProducts(){
        List<Product> productList=List.of(
                new Product("samsung",20000,"electronics"),
                new Product("iphone13",50000,"electronics")
        );
        Mockito.when(productRepository.findAll()).thenReturn(productList);
        List<Product> allProducts = productService.findAllProducts();
        assertEquals(productList.size(),allProducts.size());
    }

    @Test
    void updateProduct(){
        Product existingProduct=product;
        Product updatedProduct=new Product("iphone 16 pro",90000,"electronics");
        updatedProduct.setId(existingProduct.getId());

        Mockito.when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product product = productService.updateProduct(existingProduct.getId(), updatedProduct);
        assertEquals(product.getId(),updatedProduct.getId());
        assertEquals(product.getName(),updatedProduct.getName());
        assertEquals(product.getPrice(),updatedProduct.getPrice());
        assertEquals(product.getCategory(),updatedProduct.getCategory());
    }

    @Test
    void deleteProduct(){
        Mockito.doNothing().when(productRepository).deleteById("1");

        productService.deleteProduct("1");

        // Verify that deleteById was called exactly once with the productId
        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    void testProductNameIsEmptyValidation(){
        product.setName("");
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            productService.create(product);
        });
        assertEquals("product name should not be empty",runtimeException.getMessage());
        verify(productRepository,times(0)).save(Mockito.any(Product.class));
    }
}