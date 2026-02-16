package pro1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Path inputDir = Paths.get("input");
        Path outputDir = Paths.get("output");

        // 1. Vytvoření složky output, pokud neexistuje
        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
        } catch (IOException e) {
            System.err.println("Nepodařilo se vytvořit složku output: " + e.getMessage());
            return;
        }

        System.out.println("Zpracovávám soubory...");

        try (Stream<Path> paths = Files.walk(inputDir)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(sourcePath -> {
                        // Vytvoříme cestu pro výstupní soubor: output/nazev_souboru.csv
                        Path destPath = outputDir.resolve(sourcePath.getFileName());

                        processFile(sourcePath, destPath);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hotovo. Zkontroluj složku 'output'.");
    }

    private static void processFile(Path source, Path destination) {
        // Použijeme BufferedWriter pro zápis do souboru
        try (BufferedWriter writer = Files.newBufferedWriter(destination)) {
            Files.lines(source).forEach(line -> {
                if (line.trim().isEmpty()) return;

                try {
                    String[] parts = line.split("[,;\t=:]", 2);
                    String outputLine;

                    if (parts.length > 1) {
                        String name = parts[0].trim();
                        String originalValue = parts[1].trim();
                        String calculatedValue;

                        try {
                            Fraction result = Fraction.parseExpression(originalValue);
                            calculatedValue = result.toString();
                        } catch (Exception e) {
                            // Pokud se nepodaří spočítat, necháme původní hodnotu
                            calculatedValue = originalValue;
                        }

                        // Sestavíme řádek pro CSV (jméno, hodnota)
                        outputLine = name + ", " + calculatedValue;
                    } else {
                        // Řádek bez hodnoty jen opíšeme
                        outputLine = line;
                    }

                    // Zápis do souboru + nový řádek
                    writer.write(outputLine);
                    writer.newLine();

                } catch (IOException e) {
                    System.err.println("Chyba při zápisu řádku: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Chyba při zpracování souboru " + source + ": " + e.getMessage());
        }
    }

    // --- Třída Fraction zůstává stejná ---
    static class Fraction {
        long numerator;
        long denominator;

        public Fraction(long numerator, long denominator) {
            if (denominator == 0) throw new ArithmeticException("Dělení nulou");
            if (denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }
            this.numerator = numerator;
            this.denominator = denominator;
            simplify();
        }

        private void simplify() {
            if (numerator == 0) {
                denominator = 1;
                return;
            }
            long gcd = gcd(Math.abs(numerator), denominator);
            numerator /= gcd;
            denominator /= gcd;
        }

        private static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        public Fraction add(Fraction other) {
            long newNum = this.numerator * other.denominator + other.numerator * this.denominator;
            long newDenom = this.denominator * other.denominator;
            return new Fraction(newNum, newDenom);
        }

        public static Fraction parseExpression(String expression) {
            Fraction sum = new Fraction(0, 1);
            String[] terms = expression.split("\\+");
            for (String term : terms) {
                sum = sum.add(parseTerm(term));
            }
            return sum;
        }

        private static Fraction parseTerm(String term) {
            String clean = term.replace(" ", "").trim();
            if (clean.endsWith("%")) {
                long val = Long.parseLong(clean.substring(0, clean.length() - 1));
                return new Fraction(val, 100);
            } else if (clean.contains("/")) {
                String[] parts = clean.split("/");
                long n = Long.parseLong(parts[0]);
                long d = Long.parseLong(parts[1]);
                return new Fraction(n, d);
            } else {
                return new Fraction(Long.parseLong(clean), 1);
            }
        }

        @Override
        public String toString() {
            if (denominator == 1) return String.valueOf(numerator);
            return numerator + "/" + denominator;
        }
    }
}