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

        if (!Files.exists(dirPath)) {
            System.err.println("Directory does not exist: " + dirPath);
            return;
        }

        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(path -> {
                        try {
                            Files.lines(path).forEach(line -> {
                                String[] parts = line.split("[,;\t=:]");
                                System.out.println(parts[0]);
                                System.out.print("; "+ parts[1] +"; ");
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
