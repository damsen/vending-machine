package com.example.vendingmachine.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveProduct {

    String name;
    Double price;
    Integer quantity;
    ProductType type;
}
