package pro1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){
        System.out.println("ahoj krásný světe");
        Path dirPath = Paths.get("input");
        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(path -> {
                        try {
                            Files.lines(path).forEach(line -> {
                                String[] parts = line.split("[,;\t=:]");
                                System.out.print(parts[0].trim());
                                System.out.println(","+ parts[1].trim());

                            });
                        } catch (IOException e) {
                            System.exit(1);
                        }
                    });
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
