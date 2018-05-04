import java.math.BigInteger;
import java.util.Scanner;

public class ExpandingKnownEulerFunc {
    public void expandingKnownEulerFunc(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число n");
        BigInteger n = scanner.nextBigInteger();
        System.out.println("Введите значение функции Эйлера для числа n");
        BigInteger phi = scanner.nextBigInteger();

        BigInteger b = n.subtract(phi).add(BigInteger.ONE);

        BigInteger D = b.pow(2).subtract(n.multiply(BigInteger.valueOf(4)));
        BigInteger sqrtD = BigInteger.valueOf((long) Math.sqrt(D.doubleValue()));

        BigInteger x1 = ((b.negate()).subtract(sqrtD)).divide(BigInteger.valueOf(2));
        BigInteger x2 = ((b.negate()).add(sqrtD)).divide(BigInteger.valueOf(2));


        System.out.println("p = " + x1.negate());
        System.out.println("q = " + x2.negate());
    }
}
