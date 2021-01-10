package com.example.vendingmachine;

import com.example.vendingmachine.command.Command;

import java.util.stream.Stream;

public class VendingMachineMenu {

    public static final String COMMAND_TEMPLATE = "{%s}: to %s%n";

    public static void showMainMenu() {
        System.out.println();
        System.out.println("Welcome to the Vending Machine.");
        System.out.println("Please type one of the following commands within brackets:");
        printCommands(Command.userCommands());
    }

    public static void showAdminMenu() {
        System.out.println();
        System.out.println("Welcome to the Admin Console.");
        System.out.println("Please type one of the following commands within brackets:");
        printCommands(Command.adminCommands());
    }

    private static void printCommands(Stream<Command> commands) {
        commands.forEach(command -> System.out.printf(COMMAND_TEMPLATE, command.getKey(), command.getDescription()));
    }
}
