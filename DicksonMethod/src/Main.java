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

        Scanner scanner = new Scanner(System.in);
        BufferedReader inPrime = new BufferedReader(new FileReader("prime.txt"));

        BigInteger n = scanner.nextBigInteger();

        int h = (int) Math.sqrt(Math.exp(Math.sqrt(Math.log(n.doubleValue())*Math.log(Math.log(n.doubleValue())))));
        String prime = inPrime.readLine();
        List<Integer> base = new ArrayList<>();
        while (prime != null){
            if (Integer.parseInt(prime) <= h)
            base.add(Integer.parseInt(prime));
            prime = inPrime.readLine();
        }

        while (true) {
            List<BigInteger> numberB = new ArrayList<>();
            List<List<Integer>> alpha = new ArrayList<>();
            List<List<Integer>> e = new ArrayList<>();
            while (alpha.size() != base.size() + 1) {
                BigInteger b = BigInteger.ONE;
                while (true) {
                    while (b.compareTo(BigInteger.valueOf((long) Math.sqrt(n.doubleValue()))) < 0 || b.compareTo(n) > 0)
                        b = new BigInteger((int) (Math.random() * n.bitLength() + 2), SecureRandom.getInstance("SHA1PRNG"));
                    int k = 0;
                    for (int i = 0; i < numberB.size(); i++) {
                        if (b.equals(numberB.get(i)))
                            k++;
                    }
                    if (k == 0)
                        break;
                }
                BigInteger a = b.modPow(BigInteger.valueOf(2), n);

                if (isB(base, a)) {
                    List<Integer> array1 = B(base, a);
                    List<Integer> array2 = new ArrayList<>();
                    for (int i = 0; i < array1.size(); i++) {
                        array2.add(array1.get(i) % 2);
                    }
                    alpha.add(array1);
                    e.add(array2);
                    numberB.add(b);
                }
            }
            List<List<Integer>> ans = new ArrayList<>();
            List<Integer> index;
            for (int i = 0; i < e.size(); i++) {
                if (ans.size() > 0)
                    break;
                List<Boolean> used = new ArrayList<>();
                List<Integer> array = e.get(i);
                index = new ArrayList<>();
                for (int j = 0; j < e.size(); j++) {
                    used.add(false);
                }
                used.set(i, true);
                index.add(i);
                nextStep(e, array, index, used, ans);
            }

            BigInteger x = BigInteger.ONE;
            for (int i = 0; i < ans.get(0).size(); i++) {
                x = x.multiply(numberB.get(ans.get(0).get(i))).mod(n);
                if (x.equals(BigInteger.ZERO))
                    x = n;
            }

            BigInteger y = BigInteger.ONE;
            for (int i = 0; i < base.size(); i++) {
                BigInteger step = BigInteger.ZERO;
                for (int j = 0; j < ans.get(0).size(); j++) {
                    step = step.add(BigInteger.valueOf(alpha.get(ans.get(0).get(j)).get(i)));
                }
                step = step.divide(BigInteger.valueOf(2));
                y = y.multiply(BigInteger.valueOf(base.get(i)).modPow(step, n)).mod(n);
            }

            if (!x.equals(y) && !x.subtract(n).equals(y.negate())) {
                BigInteger p = gcd(x.add(y), n);
                BigInteger q = gcd(x.add(n).subtract(y).mod(n), n);
                if (p.multiply(q).equals(n)) {
                    System.out.println("p = " + p);
                    System.out.println("q = " + q);
                    break;
                }
                else if (n.mod(p).equals(BigInteger.ZERO) || n.mod(q).equals(BigInteger.ZERO)) {
                    if (n.mod(p).equals(BigInteger.ZERO))
                        q = n.divide(p);
                    else if (n.mod(q).equals(BigInteger.ZERO))
                        p = n.divide(q);
                    System.out.println("p = " + p);
                    System.out.println("q = " + q);
                    break;
                }
            }
        }
    }

    private List<List<Integer>> nextStep(List<List<Integer>> e, List<Integer> array, List<Integer> index, List<Boolean> used, List<List<Integer>> ans) {

        if (isNull(array)) {
            List<Integer> cl = new ArrayList<>();
            for (int i = 0; i < index.size(); i++) {
                cl.add(index.get(i));
            }
            ans.add(cl);
        }

        List<Integer> next = new ArrayList<>();
        for (int i = 0; i < e.size(); i++) {
            if (!used.get(i)) {
                next.add(i);
            }
        }
        for (int i = 0; i < next.size(); i++) {
            for (int j = 0; j < e.get(next.get(i)).size(); j++) {
                array.set(j, (array.get(j) + e.get(next.get(i)).get(j) + 2) % 2);
            }
            index.add(next.get(i));
            used.set(next.get(i), true);
            nextStep(e, array, index, used, ans);
            used.set(next.get(i), false);
            for (int j = 0; j < e.get(next.get(i)).size(); j++) {
                array.set(j, (array.get(j) + e.get(next.get(i)).get(j) + 2) % 2);
            }
            index.remove(index.size() - 1);
        }

        return ans;
    }

    private boolean isNull(List<Integer> smallE){
        for (int i = 0; i < smallE.size(); i++) {
            if (smallE.get(i) == 1)
                return false;
        }
        return true;
    }

    private boolean isB(List<Integer> base, BigInteger a){
        while (true) {
            boolean flag = false;
            for (int i = 0; i < base.size(); i++) {
                if (a.mod(BigInteger.valueOf(base.get(i))).equals(BigInteger.ZERO)){
                    flag = true;
                    a = a.divide(BigInteger.valueOf(base.get(i)));
                    break;
                }
            }

            if (!flag)
                return false;
            if (a.equals(BigInteger.ONE))
                return true;
        }
    }

    private List<Integer> B(List<Integer> base, BigInteger a){
        List<Integer> alpha = new ArrayList<>();
        for (int i = 0; i < base.size(); i++) {
            alpha.add(0);
        }
        while (true) {
            boolean flag = false;
            for (int i = 0; i < base.size(); i++) {
                if (a.mod(BigInteger.valueOf(base.get(i))).equals(BigInteger.ZERO)){
                    alpha.set(i, alpha.get(i) + 1);
                    a = a.divide(BigInteger.valueOf(base.get(i)));
                    flag = true;
                    break;
                }
            }

            if (!flag)
                break;
        }
        return alpha;
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
