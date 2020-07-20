package com.example.vendingmachine;

import com.example.vendingmachine.command.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.stream.Stream;

public class VendingMachineMenu {

    public static final String COMMAND_TEMPLATE = "{%s}: to %s";

    public static void showMainMenu() {
        System.out.println();
        System.out.println("Welcome to the Vending Machine.");
        System.out.println("Please insert one of the following:");
        printCommands(Command.userCommands());
    }

    public static void showAdminMenu() {
        System.out.println();
        System.out.println("Welcome to the Admin Console.");
        System.out.println("Please insert one of the following:");
        printCommands(Command.adminCommands());
    }

    private static void printCommands(Stream<Command> commands) {
        commands.forEach(command -> System.out.println(String.format(COMMAND_TEMPLATE, command.getKey(), command.getDescription())));
    }
}
