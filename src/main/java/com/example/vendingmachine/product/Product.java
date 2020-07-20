package com.example.vendingmachine.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue
    Integer productId;
    @Column(unique = true)
    String name;
    BigDecimal price;
    Integer quantity;
    ProductType type;

    public static Product of(String name, double price, int quantity, ProductType type) {
        return new Product(null, name, BigDecimal.valueOf(price), quantity, type);
    }

    public static Product from(SaveProduct save) {
        return new Product(null, save.getName(), BigDecimal.valueOf(save.getPrice()), save.getQuantity(), save.getType());
    }

    public static Example<Product> example(SaveProduct save) {
        return Example.of(new Product(null, save.getName(), BigDecimal.valueOf(save.getPrice()), null, save.getType()));
    }

    public Product decrementQuantity() {
        if (this.quantity == 0) {
            throw new ProductOutOfStockException();
        }
        this.quantity--;
        return this;
    }

    public Product incrementQuantity(int quantity) {
        this.quantity += quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Name='" + name + '\'' +
               ", Price=" + price +
               ", Quantity=" + quantity;
    }
}
