package pro1;
import  org.junit.jupiter.api.Assertions;

public class FractionTest1
{
    @org.junit.jupiter.api.Test
    void test()
    {
        Assertions.assertEquals("12/25",new Fraction(72,150).vratZlomek());

    }
}