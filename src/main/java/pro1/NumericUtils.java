package pro1;

public class NumericUtils {
    public static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            System.out.println(a + ";" + b);
            a = b;
            b = tmp;
        }
        return Math.abs(a);
    }
}
