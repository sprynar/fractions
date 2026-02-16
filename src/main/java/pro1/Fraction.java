package pro1;

public class Fraction {
    long n; // čitatel
    long d; // jmenovatel

    public Fraction(long n, long d) {
        long spolecnyDelitel = NumericUtils.gcd(n, d);
        this.n = n / spolecnyDelitel;
        this.d = d / spolecnyDelitel;
    }
    public String vratZlomek() {
        return n + "/" + d;
    }
}