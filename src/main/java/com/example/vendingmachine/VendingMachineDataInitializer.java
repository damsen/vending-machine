package com.example.vendingmachine;

import com.example.vendingmachine.product.Product;
import com.example.vendingmachine.product.ProductRepository;
import com.example.vendingmachine.product.ProductType;
import com.example.vendingmachine.stats.Stat;
import com.example.vendingmachine.stats.StatKey;
import com.example.vendingmachine.stats.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class VendingMachineDataInitializer {

    private final ProductRepository productRepo;
    private final StatRepository statsRepo;

    @Bean
    CommandLineRunner init() {
        return args -> {

            Product coke = Product.of("Coke", 1.20, 3, ProductType.DRINK);
            Product dietCoke = Product.of("Diet Coke", 1.10, 5, ProductType.DRINK);
            Product redBull = Product.of("RedBull", 1.20, 4, ProductType.DRINK);
            Product snickers = Product.of("Snickers", 0.80, 3, ProductType.CHOCOLATE);
            Product mars = Product.of("Mars", 0.70, 15, ProductType.CHOCOLATE);
            Product chewingGum = Product.of("Chewing-gum", 1.00, 3, ProductType.OTHER);

            productRepo
                    .saveAll(List.of(coke, dietCoke, redBull, snickers, mars, chewingGum))
                    .forEach(System.out::println);

            Stat totalAmountSold = Stat.of(StatKey.AMOUNT_SOLD, "Total amount sold", "0.0");
            Stat totalProductsDelivered = Stat.of(StatKey.PRODUCTS_DELIVERED, "Total products delivered", "0");

            statsRepo
                    .saveAll(List.of(totalAmountSold, totalProductsDelivered))
                    .forEach(System.out::println);
        };
    }

}
