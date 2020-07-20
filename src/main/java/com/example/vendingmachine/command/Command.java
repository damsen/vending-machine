package com.example.vendingmachine.command;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Command {

    LOGIN("login"),
    LOGOUT("logout"),
    PRODUCTS("products"),
    BUY("buy"),
    MACHINE_STATE("machine-state"),
    QUIT("quit");

    private final String key;

    Command(String key) {
        this.key = key;
    }

    public static Command from(String key) {
        return Stream.of(values())
                .filter(command -> command.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException(key));
    }

}
