import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Task2 {

    public void task() throws IOException {


        BufferedReader inCipher = new BufferedReader(new FileReader("task2_inputCipher.txt"));
        BufferedReader inNumbers = new BufferedReader(new FileReader("task2_twoNumber.txt"));
        BufferedReader inH0 = new BufferedReader(new FileReader("task2_h0.txt"));
        BufferedReader inAlph = new BufferedReader(new FileReader("task2_alph.txt"));
        PrintWriter outHdi = new PrintWriter("task2_hdi.txt");
        PrintWriter out = new PrintWriter("task2_output.txt");
        DecimalFormat formattedDouble = new DecimalFormat("#0.00000000");

        String alph = inAlph.readLine();

        int n1 = Integer.parseInt(inNumbers.readLine());
        int n2 = Integer.parseInt(inNumbers.readLine());

        StringBuilder cipher = new StringBuilder();
        String s = inCipher.readLine();
        while (s != null) {
            s = s.toLowerCase();
            for (int i = 0; i < s.length(); i++) {
                if (alph.indexOf(s.charAt(i)) == -1) {
                    s = s.substring(0, i) + s.substring(i + 1, s.length());
                    i--;
                }
            }
            cipher.append(s);
            s = inCipher.readLine();
        }

        List<List<Double>> hd = new ArrayList<>();
        for (int i = n1; i <= n2; i++) {

            int n = cipher.length();
            int t = n / i;
            int r = n % i;

            List<Integer> z = new ArrayList<>();
            for (int j = 0; j < (t - 1) * i + r; j++) {
                int zi = (alph.indexOf(cipher.charAt(j)) - alph.indexOf(cipher.charAt(j + i)) + alph.length()) % alph.length();
                z.add(zi);
            }

            HashMap<Integer, Integer> intQuantity = new HashMap<>();
            for (int j = 0; j < alph.length(); j++) {
                intQuantity.put(j, 0);
            }
            for (int j = 0; j < z.size(); j++) {
                int number = intQuantity.get(z.get(j));
                intQuantity.replace(z.get(j), number + 1);
            }
            List<Double> hdi = new ArrayList<>();
            for (Integer integer : intQuantity.keySet()) {
                hdi.add(((double) intQuantity.get(integer)) / z.size());
            }
            hd.add(hdi);
        }

        for (int i = 0; i < alph.length(); i++) {
            outHdi.print("P_" + i + " = ");
            for (int j = 0; j < hd.size(); j++) {
                outHdi.print(formattedDouble.format(hd.get(j).get(i)) + " ");
            }
            outHdi.println();
        }

        List<Double> h0 = new ArrayList<>();
        String pi = inH0.readLine();
        while (pi != null) {
            double numbPi = Double.parseDouble(pi.substring(pi.indexOf("=") + 1, pi.length()));
            h0.add(numbPi);
            pi = inH0.readLine();
        }
        Double minNumber = Double.MAX_VALUE;
        int index = 0;
        List<Double> ans = new ArrayList<>();
        for (int i = 0; i < hd.size(); i++) {
            Double dif = 0.D;
            for (int j = 0; j < hd.get(i).size(); j++) {
                dif += (hd.get(i).get(j) - h0.get(j)) * (hd.get(i).get(j) - h0.get(j));
            }
            Double newMin = Math.sqrt(dif);
            if (newMin < minNumber) {
                minNumber = newMin;
                index = i;
            }
            ans.add(newMin);
        }

        /*for (int i = 0; i < ans.size(); i++) {
            System.out.println(ans.get(i) + " ");
        }*/

        System.out.println(index + n1);

        inAlph.close();
        inCipher.close();
        inNumbers.close();
        inH0.close();
        out.close();
        outHdi.close();
    }
}
