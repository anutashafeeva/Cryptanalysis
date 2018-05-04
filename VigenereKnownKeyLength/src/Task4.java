import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Task4 {

    public void task4() throws IOException {

        BufferedReader inCipher = new BufferedReader(new FileReader("task4_cipher.txt"));
        BufferedReader inPeriod = new BufferedReader(new FileReader("task4_periodicity.txt"));
        BufferedReader inAlph = new BufferedReader(new FileReader("task4_alph.txt"));
        BufferedReader inLength = new BufferedReader(new FileReader("task4_length.txt"));
        PrintWriter out = new PrintWriter("task4_output.txt");
        PrintWriter outAns = new PrintWriter("task4_answer.txt");

        int length = Integer.parseInt(inLength.readLine());

        StringBuilder cipher = new StringBuilder();
        String c = inCipher.readLine();
        while (c != null) {
            cipher.append(c);
            c = inCipher.readLine();
        }

        String alph = inAlph.readLine();

        List<Pair<String, Double>> period = new ArrayList<>();
        String p = inPeriod.readLine();
        while (p != null) {
            period.add(new Pair<>(p.substring(0, p.indexOf('-') - 1), Double.parseDouble(p.substring(p.indexOf('-') + 1))));
            p = inPeriod.readLine();
        }

        List<StringBuilder> ans = new ArrayList<>();
        for (int ii = 0; ii < 200; ii++) {

            String key = period.get(ii).getKey();

            for (int i = 0; i < key.length(); i++) {
                StringBuilder strAns = new StringBuilder();
                int posK = 0;
                for (int j = 0; j < cipher.length(); j++) {
                    strAns.append(alph.charAt((alph.indexOf(cipher.charAt(j)) - alph.indexOf(key.charAt(posK)) + alph.length()) % alph.length()));
                    posK = (posK + 1) % key.length();
                }
                ans.add(strAns);
                key = key.substring(1) + key.substring(0, 1);
            }
        }

        Map<String, Integer> answer = new TreeMap<>();
        for (int i = 0; i < ans.size(); i++) {
            int count = 3;
            boolean flag = false;
            while (count <= 3) {
                for (int jj = 0; jj < ans.get(i).length() - count; jj++) {
                    String find = ans.get(i).substring(jj, jj + count);
                    for (int j = jj + 1; j < ans.get(i).length() - count; j++) {
                        String newFind = ans.get(i).substring(j, j + count);
                        if (find.equals(newFind)) {
                            flag = true;
                            if (answer.containsKey(newFind))
                                answer.put(find, answer.get(find) + 1);
                            else
                                answer.put(find, 2);
                        }
                    }
                }
                if (flag)
                    count++;
                else
                    break;
            }
        }

        int max = 2;
        for (String keyString: answer.keySet()) {
            if (answer.get(keyString) > max)
                max = answer.get(keyString);
            out.println(keyString + " " + answer.get(keyString));
        }

        System.out.println(max);

        List<String> keySet = new ArrayList<>();
        for (String strFromMap : answer.keySet()) {
            if (answer.get(strFromMap) >= 10) {
                keySet.add(strFromMap);
            }
        }

        List<String> ansEnd = new ArrayList<>();
        for (String keyStr : answer.keySet()) {
            String newS = keyStr;
            nextStep(outAns, newS, length, keySet, ansEnd);
        }

        inAlph.close();
        inCipher.close();
        inPeriod.close();
        outAns.close();
        out.close();
    }

    private List<String> nextStep(PrintWriter out, String s, int length, List<String> array, List<String> ansEnd) {

        List<String> next = new ArrayList<>();
        if (s.length() < length) {
            for (int i = 0; i < array.size(); i++) {
                if (s.charAt(s.length() - 2) == array.get(i).charAt(0) && s.charAt(s.length() - 1) == array.get(i).charAt(1)){
                    next.add(array.get(i));
                }
            }

            for (int i = 0; i < next.size(); i++) {
                s += next.get(i).substring(2);
                nextStep(out, s, length, array, ansEnd);
                s = s.substring(0, s.length() - 1);
            }
        }
        if (s.length() == length) {
            out.println(s);
        }

        return ansEnd;
    }
}
