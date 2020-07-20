package com.example.vendingmachine.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statsRepo;

    public double getTotalAmountSold() {
        return statsRepo.findByKey(StatKey.AMOUNT_SOLD)
                .map(Stat::getValue)
                .map(Double::parseDouble)
                .orElseThrow(() -> new StatNotFoundException(StatKey.AMOUNT_SOLD.name()));
    }

    public int getTotalProductsDelivered() {
        return statsRepo.findByKey(StatKey.PRODUCTS_DELIVERED)
                .map(Stat::getValue)
                .map(Integer::parseInt)
                .orElseThrow(() -> new StatNotFoundException(StatKey.PRODUCTS_DELIVERED.name()));
    }

    public void incrementTotalAmountSold(BigDecimal productPrice) {
        updateStat(StatKey.AMOUNT_SOLD, stat -> {
            BigDecimal amountSold = BigDecimal.valueOf(Double.parseDouble(stat.getValue()));
            String incrementedValue = String.valueOf(amountSold.add(productPrice));
            stat.setValue(incrementedValue);
            return stat;
        });
    }

    public void incrementTotalProductsDelivered() {
        updateStat(StatKey.PRODUCTS_DELIVERED, stat -> {
            int productsDelivered = Integer.parseInt(stat.getValue());
            String incrementedValue = String.valueOf(++productsDelivered);
            stat.setValue(incrementedValue);
            return stat;
        });
    }

    private void updateStat(StatKey key, Function<Stat, Stat> update) {
        statsRepo.findByKey(key)
                .map(update)
                .map(statsRepo::save)
                .orElseThrow(() -> new StatNotFoundException(key.name()));
    }
}
