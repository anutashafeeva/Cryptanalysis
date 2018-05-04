import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().start();
    }
    private void start(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите нечетное число n");
        BigInteger number = scanner.nextBigInteger();
        if (number.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
        {
            System.out.println("Число n четное");
            return;
        }
        System.out.println("Введите начальное значение c: 1 < c < n");
        BigInteger c = scanner.nextBigInteger();
        if (c.compareTo(BigInteger.ONE) == -1 || c.compareTo(number) == 1){
            System.out.println("Число n четное");
            return;
        }
        System.out.println("Будем использовать сжимающую функцию f(x) = x^2 + 1 (mod n)");

        BigInteger p = method(number, c);
        if (!p.equals(BigInteger.ONE.negate()))
            System.out.println("Нетривиальный делитель числа " + number + " = " + p);
    }

    private BigInteger method(BigInteger n, BigInteger c){
        BigInteger a = c;
        BigInteger b = c;
        while (true) {
            a = a.multiply(a).add(BigInteger.ONE).mod(n);
            if (a.equals(BigInteger.ZERO))
                a = n;
            b = b.multiply(b).add(BigInteger.ONE).mod(n);
            if (b.equals(BigInteger.ZERO))
                b = n;
            b = b.multiply(b).add(BigInteger.ONE).mod(n);
            if (b.equals(BigInteger.ZERO))
                b = n;
            BigInteger number = (a.add(n).subtract(b)).mod(n);
            if (number.equals(BigInteger.ZERO))
                number = n;
            BigInteger d = gcd(number, n);
            if (d.compareTo(BigInteger.ONE) == 1 && d.compareTo(n) == -1) {
                return d;
            }
            if (d.equals(n)) {
                System.out.println("Делитель найти нельзя");
                return BigInteger.ONE.negate();
            }
            if (d.equals(BigInteger.ONE))
                continue;
        }
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        while (true) {
            if (a.compareTo(b) == 1) {
                if (!a.mod(b).equals(BigInteger.ZERO))
                    a = a.mod(b);
                else return b;
            } else {
                if (!b.mod(a).equals(BigInteger.ZERO))
                    b = b.mod(a);
                else return a;
            }
        }
    }
}
