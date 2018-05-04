import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Method {

    public void method() throws IOException {
        BufferedReader inBase = new BufferedReader(new FileReader("task2_base.txt"));
        BufferedReader inNumber = new BufferedReader(new FileReader("task2_number.txt"));
        PrintWriter out = new PrintWriter("task2_output.txt");

        BigInteger number = new BigInteger(inNumber.readLine());

        List<BigInteger> base = new ArrayList<>();
        String baseStr = inBase.readLine();
        while (baseStr != null){
            base.add(new BigInteger(baseStr));
            baseStr = inBase.readLine();
        }

        int pos = 0;
        Map<BigInteger, Integer> ans = new TreeMap<>();
        while (!isPrime(number)){
            if (pos == base.size()) {
                System.out.println("Невозможно разложить число");
                return;
            }
            BigInteger n1 = gcd(number, base.get(pos));
            if (!n1.equals(BigInteger.ONE)){
                number  = number.divide(n1);
                while (!isPrime(n1)) {
                    for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n1.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)) != 1; i = i.add(BigInteger.ONE)) {
                        if (n1.mod(i).equals(BigInteger.ZERO)){
                            if (ans.containsKey(i))
                                ans.put(i, ans.get(i) + 1);
                            else
                                ans.put(i, 1);
                            n1 = n1.divide(i);
                            break;
                        }
                    }
                }
                if (n1.compareTo(BigInteger.ONE) == 1){
                    if (ans.containsKey(n1))
                        ans.put(n1, ans.get(n1) + 1);
                    else
                        ans.put(n1, 1);
                }
            }
            else pos++;
        }

        if (number.compareTo(BigInteger.ONE) == 1) {
            if (ans.containsKey(number))
                ans.put(number, ans.get(number) + 1);
            else
                ans.put(number, 1);
        }

        for (BigInteger numbers: ans.keySet()) {
            if (ans.get(numbers) == 1)
                out.println(numbers);
            else
                out.println(numbers + "^" + ans.get(numbers));
        }

        inBase.close();
        inNumber.close();
        out.close();
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

    private boolean isPrime(BigInteger n){
        if (n.equals(BigInteger.valueOf(2))) return true;
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) return false;
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(n.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)) != 1; i  = i.add(BigInteger.valueOf(2))) {
            if (n.mod(i).equals(BigInteger.ZERO))
                return false;
        }
        return true;
    }
}
