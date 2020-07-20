package com.example.vendingmachine.product;

public class ProductNotFoundException extends RuntimeException {

    public static final String PRODUCT_NOT_FOUND = "Product not found with name %s.";

    public ProductNotFoundException(String name) {
        super(String.format(PRODUCT_NOT_FOUND, name));
    }

}
