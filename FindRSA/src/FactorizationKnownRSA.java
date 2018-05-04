import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class FactorizationKnownRSA {
    public void factorizationKnownRSA() throws IOException, NoSuchAlgorithmException {

        BufferedReader inPublic = new BufferedReader(new FileReader("task3_publicKey.txt"));
        BufferedReader inSecret = new BufferedReader(new FileReader("task3_secretKey.txt"));

        BigInteger n = new BigInteger(inPublic.readLine());
        BigInteger e = new BigInteger(inPublic.readLine());
        BigInteger d = new BigInteger(inSecret.readLine());

        BigInteger newNumber = e.multiply(d).subtract(BigInteger.ONE);
        while (newNumber.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            newNumber = newNumber.divide(BigInteger.valueOf(2));
        }

        while (true) {

            BigInteger a = BigInteger.ONE;
            while (a.compareTo(BigInteger.ONE) != 1 || a.compareTo(n.subtract(BigInteger.ONE)) != -1)
                a = new BigInteger((int) (Math.random() * n.bitLength() + 2), SecureRandom.getInstance("SHA1PRNG"));

            BigInteger u = a.modPow(newNumber, n);
            BigInteger v = u.modPow(BigInteger.valueOf(2), n);

            while (!v.equals(BigInteger.ONE)) {
                u = v;
                v = u.modPow(BigInteger.valueOf(2), n);
                if (u.equals(BigInteger.ONE.negate()) || u.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (v.equals(BigInteger.ONE)) {
                System.out.println("p = " + gcd(u.add(n).subtract(BigInteger.ONE), n));
                System.out.println("q = " + gcd(u.add(BigInteger.ONE), n));
                break;
            }
            a = a.add(BigInteger.ONE);
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
