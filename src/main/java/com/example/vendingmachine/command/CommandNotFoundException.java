package com.example.vendingmachine.command;

public class CommandNotFoundException extends RuntimeException {

    public static final String COMMAND_NOT_FOUND = "Command not found with key %s.";

    public CommandNotFoundException(String key) {
        super(String.format(COMMAND_NOT_FOUND, key));
    }
}
