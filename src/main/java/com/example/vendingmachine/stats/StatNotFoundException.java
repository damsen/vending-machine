package com.example.vendingmachine.stats;

public class StatNotFoundException extends RuntimeException {

    public static final String STAT_NOT_FOUND = "Stat not found with key %s.";

    public StatNotFoundException(String key) {
        super(String.format(STAT_NOT_FOUND, key));
    }
}
