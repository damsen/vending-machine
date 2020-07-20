package com.example.vendingmachine.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductOutOfStockException extends RuntimeException {

    private String reason;
}
