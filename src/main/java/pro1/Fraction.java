package pro1;

public class Fraction {
    private long n, d;

    public Fraction(long n, long d) {
        if (d == 0) System.exit(1);
        if (d < 0) { n = -n; d = -d; }

        long a = Math.abs(n), b = d;
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }

        this.n = n / a;
        this.d = d / a;
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
        return res;
    }

    @Override
    public String toString() {
        return d == 1 ? "" + n : n + "/" + d;
    }
}