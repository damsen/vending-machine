package com.example.vendingmachine.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MachineStateService {

    private static final Path machineStatePath;

    private final StatService statService;

    static {
        String home = System.getProperty("user.home");
        String desktop = "Desktop";
        String fileName = "machine-state.txt";
        machineStatePath = Paths.get(home, desktop, fileName);
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void writeMachineState() throws IOException {

        String line1 = "Machine state - " + new Date();
        String line2 = "Total amount sold: " + statService.getTotalAmountSold() + "€";
        String line3 = "Total product delivered: " + statService.getTotalProductsDelivered();
        String result = String.join("\n", line1, line2, line3);

        Files.write(machineStatePath, result.getBytes());
    }

    public void readMachineState() throws IOException {
        try (Stream<String> lines = Files.lines(machineStatePath, Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        }
    }

}
