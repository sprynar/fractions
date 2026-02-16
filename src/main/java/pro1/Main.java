package pro1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.println("ahoj krásný světe");
        Path dirPath = Paths.get("input");

        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(path -> {
                        try {
                            Files.lines(path).forEach(line -> {
                                if (line.trim().isEmpty()) return;

                                // Rozdělíme na 2 části podle prvního oddělovače
                                String[] parts = line.split("[,;\t=:]", 2);

                                if (parts.length > 0) {
                                    System.out.print(parts[0].trim());
                                }

                                if (parts.length > 1) {
                                    String originalValue = parts[1].trim();
                                    try {
                                        // Zkusíme vypočítat
                                        Fraction result = Fraction.parseExpression(originalValue);
                                        System.out.println(", " + result);
                                    } catch (Exception e) {
                                        // Když to selže (např. text místo čísel), vypíšeme původní text
                                        System.out.println(", " + originalValue);
                                    }
                                } else {
                                    System.out.println();
                                }
                            });
                        } catch (IOException e) {
                            // Ignorujeme chyby čtení souboru
                        }
                    });
        } catch (IOException e) {
            System.exit(1);
        }
    }

    // Vnitřní třída pro počítání se zlomky
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
            // Rozdělení podle plusu
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
                //  celé číslo
                return new Fraction(Long.parseLong(clean), 1);
            }
        }

        @Override
        public String toString() {
            if (denominator == 1) {
                return String.valueOf(numerator);
            }
            return numerator + "/" + denominator;
        }
    }
}