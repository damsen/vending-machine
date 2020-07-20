package com.example.vendingmachine.command;

import lombok.Getter;

import java.util.stream.Stream;

import static com.example.vendingmachine.command.CommandAccess.ADMIN;
import static com.example.vendingmachine.command.CommandAccess.USER;

@Getter
public enum Command {

    LOGIN("login", "Login as an administrator to access the admin console.", USER),
    LOGOUT("logout", "Logout to go back to the main menu.", ADMIN),
    PRODUCTS("products", "Show the list of all products.", USER),
    BUY("buy", "Buy a product.", USER),
    MACHINE_STATE("machine-state", "Read the machine-state file.", ADMIN),
    QUIT("quit", "Stop using the vending machine.", USER);

    private final String key;
    private final String description;
    private final CommandAccess access;

    Command(String key, String description, CommandAccess access) {
        this.key = key;
        this.description = description;
        this.access = access;
    }

    public static Command from(String key) {
        return Stream.of(values())
                .filter(command -> command.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException(key));
    }

    public static Stream<Command> userCommands() {
        return commandsWithAccess(USER);
    }

    public static Stream<Command> adminCommands() {
        return commandsWithAccess(ADMIN);
    }

    private static Stream<Command> commandsWithAccess(CommandAccess access) {
        return Stream.of(values())
                .filter(command -> access.equals(command.getAccess()));
    }

}
