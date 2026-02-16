package pro1;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path inputDir = Paths.get("input");
        Path outputDir = Paths.get("output");

        try {
            if (!Files.exists(outputDir)) Files.createDirectories(outputDir);
            System.out.println("Zpracovávám soubory...");

            try (var paths = Files.list(inputDir)) {
                paths.filter(p -> p.toString().endsWith(".csv")).forEach(src -> {
                    processFile(src, outputDir.resolve(src.getFileName()));
                });
            }
            System.out.println("Povedlo se");
        } catch (IOException e) {
            System.err.println("Chyba při práci s adresáři: " + e.getMessage());
        }
    }

    private static void processFile(Path src, Path dest) {
        try {
            List<String> lines = Files.readAllLines(src);
            List<String> output = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] parts = line.split("[,;\t=:]", 2);
                if (parts.length < 2) {
                    output.add(line);
                    continue;
                }

                String name = parts[0].trim();
                String val = parts[1].trim();
                try {
                    val = Fraction.parseExpression(val).toString();
                } catch (Exception e) {
                    System.err.println("Chyba parsování na řádku: " + line);
                }
                output.add(name + "," + val);
            }
            Files.write(dest, output);
        } catch (IOException e) {
            System.err.println("Chyba u souboru " + src.getFileName() + ": " + e.getMessage());
        }
    }
}