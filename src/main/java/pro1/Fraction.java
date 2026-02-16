package pro1;

public class Fraction {
    private long n, d;

    public Fraction(long n, long d) {
        if (d == 0) System.exit(1);
        if (d < 0) { n = -n; d = -d; }
        long nejvyssi = NumericUtils.gcd(Math.abs(n), d);

        this.n = n / nejvyssi;
        this.d = d / nejvyssi;
    }

    public Fraction add(Fraction o) {
        return new Fraction(this.n * o.d + o.n * this.d, this.d * o.d);

    }

    public static Fraction parseExpression(String s) {
        Fraction res = new Fraction(0, 1);
        for (String t : s.replace(" ", "").split("\\+")) {
            if (t.endsWith("%")) {
                res = res.add(new Fraction(Long.parseLong(t.replace("%", "")), 100));
            } else if (t.contains("/")) {
                String[] p = t.split("/");
                res = res.add(new Fraction(Long.parseLong(p[0]), Long.parseLong(p[1])));
            } else {
                res = res.add(new Fraction(Long.parseLong(t), 1));
            }
        }
        System.out.println("expression" + res);
        return res;
    }

    @Override
    public String toString() {
        System.out.println("zlomek to string: " + n + "/" + d);
        return d == 1 ? "" + n : n + "/" + d;

    }
}