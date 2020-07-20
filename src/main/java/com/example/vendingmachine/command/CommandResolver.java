package com.example.vendingmachine.command;

import com.example.vendingmachine.VendingMachineMenu;
import com.example.vendingmachine.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CommandResolver {

    private final CommandHandler commandHandler;
    private final ApplicationContext ctx;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        VendingMachineMenu.showMainMenu();
        while (true) {
            try {
                Command cmd = Command.from(commandHandler.awaitForCommandKey());
                if (Command.QUIT.equals(cmd)) {
                    break;
                }
                resolve(cmd);
            } catch (CommandNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Thank you for using the Vending Machine.");
        commandHandler.close();
        System.exit(SpringApplication.exit(ctx, () -> 0));
    }

    private void resolve(Command cmd) {
        if (CommandAccess.USER.equals(cmd.getAccess()) ||
            (CommandAccess.ADMIN.equals(cmd.getAccess()) && SecurityUtils.isLoggedInAsAdmin())) {
            cmd.getCommand().accept(commandHandler);
        } else {
            System.err.println("You need admin rights to use that command.");
        }
    }

}
