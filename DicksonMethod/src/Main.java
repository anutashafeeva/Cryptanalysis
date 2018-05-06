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
        int baseSize = 0;
        while (prime != null){
            int primeNumber = Integer.parseInt(prime);
            if (primeNumber <= h) {
                //baseSize++;
                if (!n.mod(BigInteger.valueOf(primeNumber)).equals(BigInteger.ZERO) && L(n, BigInteger.valueOf(primeNumber)) == 1) {
                    base.add(primeNumber);
                    baseSize++;}
            }
            else
                break;
            prime = inPrime.readLine();
        }

        while (true) {
            List<BigInteger> numberB = new ArrayList<>();
            List<List<Integer>> alpha = new ArrayList<>();
            List<List<Integer>> e = new ArrayList<>();
            numberB = drobi(n, baseSize, base);
            if (numberB == null)
                return;
            /*while (alpha.size() != base.size() + 1) {
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
            }*/

            /*for (int ii = 0; ii < baseSize + 1; ii++) {
                List<Integer> array1 = B(base, numberB.get(ii).modPow(BigInteger.valueOf(2), n));
                List<Integer> array2 = new ArrayList<>();
                for (int i = 0; i < array1.size(); i++) {
                    array2.add(array1.get(i) % 2);
                }
                alpha.add(array1);
                e.add(array2);
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
            }*/

            for (int ii = 0; ii < 2; ii++) {
                List<Integer> array1 = B(base, numberB.get(ii).modPow(BigInteger.valueOf(2), n));
                List<Integer> array2 = new ArrayList<>();
                for (int i = 0; i < array1.size(); i++) {
                    array2.add(array1.get(i) % 2);
                }
                alpha.add(array1);
                e.add(array2);
            }

            /*BigInteger x = BigInteger.ONE;
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
            }*/

            BigInteger x = BigInteger.ONE;
            for (int i = 0; i < 2; i++) {
                x = x.multiply(numberB.get(i)).mod(n);
                if (x.equals(BigInteger.ZERO))
                    x = n;
            }

            BigInteger y = BigInteger.ONE;
            for (int i = 0; i < base.size(); i++) {
                BigInteger step = BigInteger.ZERO;
                for (int j = 0; j < 2; j++) {
                    step = step.add(BigInteger.valueOf(alpha.get(j).get(i)));
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
                else if (!p.equals(BigInteger.ONE) && !q.equals(BigInteger.ONE) && (n.mod(p).equals(BigInteger.ZERO) || n.mod(q).equals(BigInteger.ZERO))) {
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
            return ans;
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

    private List<BigInteger> drobi(BigInteger n, int baseSize, List<Integer> base){

        List<BigInteger> twoLinearVectors = new ArrayList<>();

        List<BigInteger> newNumberB = new ArrayList<>();
        List<List<Integer>> alpha = new ArrayList<>();
        List<List<Integer>> e = new ArrayList<>();

        List<BigInteger> P = new ArrayList<>(), Q = new ArrayList<>();
        P.add(BigInteger.ZERO);
        P.add(BigInteger.ONE);
        Q.add(BigInteger.ONE);
        Q.add(BigInteger.ZERO);
        int idx = 0;

        BigInteger sq = BigInteger.valueOf((long) Math.sqrt(n.doubleValue()));
        BigInteger k = BigInteger.ONE;
        List<BigInteger> chis = new ArrayList<>();
        List<BigInteger> znam = new ArrayList<>();
        List<BigInteger> ans = new ArrayList<>();
        ans.add(sq);
        chis.add(BigInteger.ONE);
        znam.add((ans.get(0)).negate());
        //boolean fl = true;
        //while (fl){
        //while (newNumberB.size() != baseSize + 1){
        while (true){
            BigInteger p1 = znam.get(znam.size() - 1).negate();
            BigInteger p2 = n.subtract(znam.get(znam.size() - 1).multiply(znam.get(znam.size() - 1)));
            if (p2.equals(BigInteger.ZERO)) {
                System.out.println("Не удается построить базу");
                return null;
            }
            k = chis.get(chis.size() - 1);
            if (gcd1(k, p2).compareTo(BigInteger.ONE) == 1){
                BigInteger g = gcd1(k, p2);
                k = k.divide(g);
                p2 = p2.divide(g);
            }
            ans.add(k.multiply(sq.add(p1)).divide(p2));
            chis.add(p2);
            znam.add(p1.subtract(p2.multiply(ans.get(ans.size() - 1))));

            BigInteger pi = (ans.get(idx).multiply(P.get(idx + 1))).add(P.get(idx));
            BigInteger qi = (ans.get(idx).multiply(Q.get(idx + 1))).add(Q.get(idx));
            P.add(pi);
            Q.add(qi);

            if (isB(base, pi.modPow(BigInteger.valueOf(2), n))) {
                newNumberB.add(pi);

                List<Integer> array1 = B(base, pi.modPow(BigInteger.valueOf(2), n));
                List<Integer> array2 = new ArrayList<>();
                for (int i = 0; i < array1.size(); i++) {
                    array2.add(array1.get(i) % 2);
                }
                alpha.add(array1);
                e.add(array2);

                for (int i = 0; i < newNumberB.size() - 1; i++) {
                    if (e.get(e.size() - 1).equals(e.get(i))) {
                        twoLinearVectors.add(pi);
                        twoLinearVectors.add(newNumberB.get(i));
                        return twoLinearVectors;
                    }
                }
            }

            idx++;

            /*for (int i = 0; i < chis.size() - 1; i++) {
                if (chis.get(chis.size() - 1).equals(chis.get(i)) && znam.get(znam.size() - 1).equals(znam.get(i))) {
                    fl = false;
                    break;
                }
            }*/
        }

        /*for (int i = 0; i < ans.size(); i++) {
            BigInteger pi = (ans.get(i).multiply(P.get(i + 1))).add(P.get(i));
            BigInteger qi = (ans.get(i).multiply(Q.get(i + 1))).add(Q.get(i));
            P.add(pi);
            Q.add(qi);
        }*/

        //return newNumberB;
    }

    private BigInteger gcd1(BigInteger a, BigInteger b){
        a = a.abs();
        b = b.abs();
        if (b.compareTo(a) == 1){
            BigInteger tmp = a;
            a = b;
            b = tmp;
        }
        while (!b.equals(BigInteger.ZERO)) {
            a = a.mod(b);
            BigInteger tmp = a;
            a = b;
            b = tmp;
        }
        return a;
    }

    private int L(BigInteger a, BigInteger p) {
        int res = 1;

        if (a.compareTo(BigInteger.ZERO) == -1 && p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))
            res *= -1;
        while (true) {
            a = a.mod(p);
            int k = 0;
            while (a.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                a = a.divide(BigInteger.valueOf(2));
                k++;
            }
            if (k % 2 == 1 && (p.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || p.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))))
                res *= -1;
            if (a.equals(BigInteger.ONE))
                break;
            BigInteger tmp = a;
            a = p;
            p = tmp;
            if (((p.subtract(BigInteger.ONE)).multiply(a.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(4))).mod(BigInteger.valueOf(2)).equals(BigInteger.ONE))
                res *= -1;
        }

        return res;
    }
}
