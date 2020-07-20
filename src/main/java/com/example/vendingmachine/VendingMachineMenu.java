package com.example.vendingmachine;

public class VendingMachineMenu {

    // TODO: make dynamic

    public static void showMainMenu() {
        System.out.println();
        System.out.println("Welcome to the Vending Machine.");
        System.out.println("Please insert one of the following:");
        System.out.println("{login} if you wish to login as an administrator.");
        System.out.println("{products} if you wish to see all the products.");
        System.out.println("{name} if you wish buy a product with that name.");
        System.out.println("{quit} if you wish to quit.");
    }

    public static void showAdminMenu() {
        System.out.println();
        System.out.println("Welcome to the Admin Console.");
        System.out.println("Please insert one of the following:");
        System.out.println("{machine-state} if you wish to read the machine-state.txt file.");
        System.out.println("{products} if you wish to see all the products.");
        System.out.println("{name} if you wish buy a product with that name.");
        System.out.println("{logout} if you wish to logout and go back to the main menu.");
    }
}
