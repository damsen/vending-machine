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
                Stream.of(commandHandler.getClass().getMethods())
                        .filter(method -> selectCommand(cmd, method))
                        .findFirst()
                        .ifPresent(this::resolveCommand);
            } catch (CommandNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Thank you for using the Vending Machine.");
        commandHandler.close();
        System.exit(SpringApplication.exit(ctx, () -> 0));
    }

    private boolean selectCommand(Command cmd, Method method) {
        CommandMapping annotation = AnnotationUtils.getAnnotation(method, CommandMapping.class);
        return annotation != null && cmd.equals(annotation.command());
    }

    private void resolveCommand(Method method) {
        try {
            CommandMapping annotation = AnnotationUtils.getAnnotation(method, CommandMapping.class);
            Assert.notNull(annotation, "Missing @CommandMapping annotation on received method.");
            if (CommandAccess.USER.equals(annotation.access()) ||
                (CommandAccess.ADMIN.equals(annotation.access()) && SecurityUtils.isLoggedInAsAdmin())) {
                method.invoke(commandHandler);
            } else {
                System.err.println("You need admin rights to use that command.");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Oops! An error occurred while trying to execute the command...");
        }
    }
}
