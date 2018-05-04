import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        new Main().start();
    }

    private void start() throws IOException, NoSuchAlgorithmException {

        BufferedReader inBase = new BufferedReader(new FileReader("base.txt"));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите нечетное число n");
        BigInteger number = scanner.nextBigInteger();
        if (number.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            System.out.println("Число n четное");
            return;
        }

        List<BigInteger> base = new ArrayList<>();
        String b = inBase.readLine();
        while (b != null) {
            base.add(new BigInteger(b));
            b = inBase.readLine();
        }

        System.out.println("Введите размер базы s");
        int s = scanner.nextInt();

        for (int i = 0; i < 1000; i++) {
            BigInteger p = method(base, number, s);
            if (!p.equals(BigInteger.ONE.negate())) {
                System.out.println("Нетривиальный делитель числа " + number + " = " + p);
                return;
            }
        }
        System.out.println("Делитель найти нельзя");

        inBase.close();
    }

    private BigInteger method(List<BigInteger> base, BigInteger number, int s) throws NoSuchAlgorithmException {
        BigInteger a = BigInteger.ONE;
        while (a.compareTo(BigInteger.valueOf(2)) < 0 || a.compareTo(number.subtract(BigInteger.ONE)) > -1)
            a = new BigInteger((int) (Math.random() * number.bitLength() + 2), SecureRandom.getInstance("SHA1PRNG"));

        BigInteger d = gcd(a, number);

        if (d.compareTo(BigInteger.ONE) == 1)
            return d;

        for (int i = 0; i < s; i++) {
            Double l = Math.log(Double.parseDouble(String.valueOf(number))) / Math.log(Double.parseDouble(String.valueOf(base.get(i))));
            a = a.modPow(base.get(i).pow(l.intValue()).mod(number), number);
        }

        if (a.equals(BigInteger.ONE))
            a = number.add(BigInteger.ONE);
        d = gcd(a.subtract(BigInteger.ONE), number);
        if (d.equals(BigInteger.ONE) || d.equals(number)) {
            return BigInteger.ONE.negate();
        }

        return d;
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
