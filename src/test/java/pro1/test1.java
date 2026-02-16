package pro1;
import org.junit.jupiter.api.Assertions;

public class test1 {
    @org.junit.jupiter.api.Test
    void test() {
        Assertions.assertEquals(
                10,
                NumericUtils.gcd(20, 50)
        );
    }
}