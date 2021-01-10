package com.example.vendingmachine.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductOutOfStockException extends RuntimeException {

    public static final String PRODUCT_UNAVAILABLE = "Sorry but this item is not available anymore.\n";

    public ProductOutOfStockException() {
        super(PRODUCT_UNAVAILABLE);
    }

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
