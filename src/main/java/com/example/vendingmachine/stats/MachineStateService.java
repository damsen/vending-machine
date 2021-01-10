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
public class MachineStateService {

    private final StatService statService;
    private final Path machineStatePath;

    public MachineStateService(StatService statService) {
        this.statService = statService;
        this.machineStatePath = Paths.get(System.getProperty("user.home"), "Desktop", "machine-state.txt");
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void writeMachineState() throws IOException {
        String line1 = String.format("Machine state - %s", new Date());
        String line2 = String.format("Total amount sold: %f€", statService.getTotalAmountSold());
        String line3 = String.format("Total product delivered: %d", statService.getTotalProductsDelivered());
        String result = String.join("\n", line1, line2, line3);
        Files.write(machineStatePath, result.getBytes());
    }

    public void readMachineState() throws IOException {
        try (Stream<String> lines = Files.lines(machineStatePath, Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        }
    }

}
