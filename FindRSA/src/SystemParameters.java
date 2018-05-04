import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class SystemParameters {
    public void systemParameters() throws NoSuchAlgorithmException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        PrintWriter outPQ = new PrintWriter("task1_p_q.txt");
        PrintWriter outPublicKey = new PrintWriter("task1_publicKey.txt");
        PrintWriter outSecretKey = new PrintWriter("task1_secretKey.txt");

        System.out.println("Введите длины чисел p и q");
        int lp = scanner.nextInt();
        int lq = scanner.nextInt();

        BigInteger p = BigInteger.probablePrime(lp, SecureRandom.getInstance("SHA1PRNG"));
        BigInteger q = BigInteger.probablePrime(lq, SecureRandom.getInstance("SHA1PRNG"));

        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = phi;
        while (true) {
            while (e.compareTo(BigInteger.ONE) != 1 || e.compareTo(phi) > -1) {
                e = new BigInteger((int) (Math.random() * phi.bitLength() + 2), SecureRandom.getInstance("SHA1PRNG"));
            }
            if (gcd(e, phi).equals(BigInteger.ONE))
                break;
        }
        BigInteger d = e.modInverse(phi);

        outPQ.println(p);
        outPQ.println(q);
        outPublicKey.println(n);
        outPublicKey.println(e);
        outSecretKey.println(d);

        outPQ.close();
        outPublicKey.close();
        outSecretKey.close();
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
