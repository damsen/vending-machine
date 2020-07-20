package com.example.vendingmachine;

import com.example.vendingmachine.command.Command;

public class VendingMachineMenu {

    public static final String COMMAND_TEMPLATE = "{%s}: to %s";

    public static void showMainMenu() {
        System.out.println();
        System.out.println("Welcome to the Vending Machine.");
        System.out.println("Please type one of the following commands within brackets:");
        Command.userCommands()
                .forEach(command -> System.out.println(String.format(COMMAND_TEMPLATE, command.getKey(), command.getDescription())));
    }

    public static void showAdminMenu() {
        System.out.println();
        System.out.println("Welcome to the Admin Console.");
        System.out.println("Please type one of the following commands within brackets:");
        Command.adminCommands()
                .forEach(command -> System.out.println(String.format(COMMAND_TEMPLATE, command.getKey(), command.getDescription())));
    }
}
