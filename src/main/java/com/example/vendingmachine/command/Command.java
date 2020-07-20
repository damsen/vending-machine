package com.example.vendingmachine.command;

import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.vendingmachine.command.CommandAccess.*;

@Getter
public enum Command {

    LOGIN("login", "Login as an administrator to access the admin console.", USER, CommandHandler::loginCommand),
    LOGOUT("logout", "Logout to go back to the main menu.", ADMIN, CommandHandler::logoutCommand),
    PRODUCTS("products", "Show the list of all products.", USER, CommandHandler::showProductsCommand),
    BUY("buy", "Buy a product.", USER, CommandHandler::buyProductCommand),
    MACHINE_STATE("machine-state", "Read the machine-state file.", ADMIN, CommandHandler::readMachineStateCommand),
    QUIT("quit", "Stop using the vending machine.", USER, commandHandler -> {});

    private final String key;
    private final String description;
    private final CommandAccess access;
    private final Consumer<CommandHandler> command;

    Command(String key, String description, CommandAccess access, Consumer<CommandHandler> command) {
        this.key = key;
        this.access = access;
        this.description = description;
        this.command = command;
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
                .filter(command -> command.getAccess().equals(access));
    }
}
