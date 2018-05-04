import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CreateBase {

    public void createBase() throws IOException {

        BufferedReader inPrime = new BufferedReader(new FileReader("task1_prime.txt"));
        BufferedReader inCount = new BufferedReader(new FileReader("task1_count.txt"));
        PrintWriter out = new PrintWriter("task1_output.txt");

        List<BigInteger> prime = new ArrayList<>();
        String primeStr = inPrime.readLine();
        while (primeStr != null){
            prime.add(new BigInteger(primeStr));
            primeStr = inPrime.readLine();
        }

        int t = Integer.parseInt(inCount.readLine());

        List<BigInteger> outPrime = new ArrayList<>();
        for (int i = 0; i < prime.size(); i += t) {
            BigInteger newPrime = BigInteger.ONE;
            if (i + t > prime.size())
                break;
            for (int j = 0; j < t; j++) {
                newPrime = newPrime.multiply(prime.get(i + j));
            }
            outPrime.add(newPrime);
        }

        for (int i = 0; i < outPrime.size(); i++) {
            out.println(outPrime.get(i));
        }

        inCount.close();
        inPrime.close();
        out.close();
    }
}
