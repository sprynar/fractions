package pro1;
import org.junit.jupiter.api.Assertions;

public class GcdTest1 {
    @org.junit.jupiter.api.Test
    void test() {
        Assertions.assertEquals(
                385,
                NumericUtils.gcd(1155, 770)
        );
    }
    @org.junit.jupiter.api.Test
    void testSoudelna() {
        Assertions.assertEquals(6, NumericUtils.gcd(48, 18));
    }

    @org.junit.jupiter.api.Test
    void testNesoudelna() {
        Assertions.assertEquals(1, NumericUtils.gcd(13, 7));
    }

    @org.junit.jupiter.api.Test
    void testNula() {
        Assertions.assertEquals(25, NumericUtils.gcd(25, 0));
    }

    @org.junit.jupiter.api.Test
    void testZapornaCisla() {
        Assertions.assertEquals(4, NumericUtils.gcd(-8, 12));
    }

    @org.junit.jupiter.api.Test
    void testJedna() {
        Assertions.assertEquals(1, NumericUtils.gcd(1, 100));
    }
}