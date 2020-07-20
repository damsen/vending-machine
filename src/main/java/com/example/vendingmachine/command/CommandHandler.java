package com.example.vendingmachine.command;

import com.example.vendingmachine.VendingMachineMenu;
import com.example.vendingmachine.product.Product;
import com.example.vendingmachine.product.ProductNotFoundException;
import com.example.vendingmachine.product.ProductOutOfStockException;
import com.example.vendingmachine.product.ProductService;
import com.example.vendingmachine.security.SecurityUtils;
import com.example.vendingmachine.stats.MachineStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final ProductService productService;
    private final MachineStateService machineStateService;
    private final AuthenticationManager authManager;

    private final Scanner scanner = new Scanner(System.in);

    public String awaitForCommandKey() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void loginCommand() {
        if (SecurityUtils.isLoggedIn()) {
            System.err.println("You are already logged in.");
            return;
        }
        System.out.println("Username:");
        String username = scanner.nextLine();
        System.out.println("Password:");
        String pw = scanner.nextLine();
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            context.setAuthentication(authManager.authenticate(new UsernamePasswordAuthenticationToken(username, pw)));
            if (SecurityUtils.isLoggedInAsAdmin()) {
                VendingMachineMenu.showAdminMenu();
            }
        } catch (BadCredentialsException ex) {
            System.err.println("Bad credentials.");
        }
    }

    public void logoutCommand() {
        if (!SecurityUtils.isLoggedIn()) {
            System.out.println("You are not logged in.");
            return;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        System.out.println("Successfully logged out.");
        VendingMachineMenu.showMainMenu();
    }

    public void showProductsCommand() {
        System.out.println("Available products:");
        productService.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getType))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> {
                    System.out.println(entry.getKey());
                    entry.getValue().forEach(System.out::println);
                });
    }

    public void buyProductCommand() {
        try {
            System.out.println("What product would you like to buy?");
            Product product = productService.buyProduct(scanner.nextLine());
            System.out.println(product.getName() + " delivered");
        } catch (ProductNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (ProductOutOfStockException ex) {
            System.err.println(ex.getReason());
        }
    }

    public void readMachineStateCommand() {
        try {
            machineStateService.readMachineState();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the machine-state file.");
        }
    }
}
