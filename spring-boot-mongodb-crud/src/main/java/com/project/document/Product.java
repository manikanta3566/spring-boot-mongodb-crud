package com.project.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "products")
@Data
public class Product {

    @Id
    private String id;
    private String name;

    private double price;

    private String category;

    public Product(){
        this.id= UUID.randomUUID().toString().split("-")[0];
    }

    public Product(String name, double price, String category) {
        this.id = UUID.randomUUID().toString().split("-")[0];
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
