import javafx.util.Pair;

import java.io.*;
import java.util.*;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class Task2 {

    void task2() throws IOException {

        Scanner scanner = new Scanner(System.in);
        BufferedReader inLeng = new BufferedReader(new FileReader("task2_length.txt"));
        BufferedReader inCipher = new BufferedReader(new FileReader("task2_cipher.txt"));
        BufferedReader inPeriod = new BufferedReader(new FileReader("task2_periodicity.txt"));
        PrintWriter out = new PrintWriter("task2_keys.txt");
        PrintWriter outPeriodTable = new PrintWriter("task2_periodTable.txt");

        int l = Integer.parseInt(inLeng.readLine());
        List<String> alph = new ArrayList<>();
        List<Pair<String, Double>> period = new ArrayList<>();
        String s = inPeriod.readLine();
        while (s != null) {
            alph.add(s.substring(0, s.indexOf('-') - 1));
            period.add(new Pair<>(s.substring(0, s.indexOf('-') - 1), Double.parseDouble(s.substring(s.indexOf('-') + 1))));
            s = inPeriod.readLine();
        }
        StringBuilder cipher = new StringBuilder();
        s = inCipher.readLine();
        while (s != null) {
            cipher.append(s);
            s = inCipher.readLine();
        }

        String c = cipher.toString();
        List<String> table = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = i; j < cipher.length(); j += l) {
                str.append(String.valueOf(c.charAt(j)));
            }
            table.add(str.toString());
        }

        List<List<Pair<Double, String>>> periodTable = new ArrayList<>();
        for (int i = 0; i < l; i++) {

            HashMap<String, Integer> charQuantity = new HashMap<>();
            for (int j = 0; j < table.get(i).length(); j++) {
                if (charQuantity.containsKey(String.valueOf(table.get(i).charAt(j)))) {
                    int number = charQuantity.get(String.valueOf(table.get(i).charAt(j)));
                    charQuantity.replace(String.valueOf(table.get(i).charAt(j)), number + 1);
                } else {
                    charQuantity.put(String.valueOf(table.get(i).charAt(j)), 1);
                }
            }

            List<Pair<Double, String>> periodicity = new ArrayList<>();
            for (String character : charQuantity.keySet()) {
                periodicity.add(new Pair<>(((double) charQuantity.get(character)), character));
            }

            periodicity.sort(Comparator.comparing(Pair::getKey));
            periodTable.add(periodicity);

            for (int j = periodicity.size() - 1; j >= 0; j--) {
                outPeriodTable.println(periodicity.get(j).getValue() + " - " + (periodicity.get(j).getKey() / table.get(i).length()));
            }

            outPeriodTable.println("\n");
        }

        alph.sort(Comparator.naturalOrder());
        if (alph.contains("ё")) {
            for (int i = alph.size() - 1; i > alph.indexOf("е"); i--) {
                alph.set(i, alph.get(i - 1));
            }
            alph.set(alph.indexOf("е") + 1, "ё");
        }

        System.out.println("Сколько частых букв хотите использовать при поиске ключа");
        int countLetter = scanner.nextInt();

        List<String> fasterLetter = new ArrayList<>();
        for (int i = 0; i < countLetter; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < l; j++) {
                String s1 = periodTable.get(j).get(periodTable.get(j).size() - i - 1).getValue();
                String s2 = period.get(0).getKey();
                int pos1 = alph.indexOf(s1);
                int pos2 = alph.indexOf(s2);
                int pos = (pos1 - pos2 + period.size()) % period.size();
                str.append(alph.get(pos));
            }
            fasterLetter.add(str.toString());
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < countLetter; i++) {
            String newS = String.valueOf(fasterLetter.get(i).charAt(0));
            nextStep(1, newS, fasterLetter, ans);
        }

        for (int i = 0; i < ans.size(); i++) {
            out.println(ans.get(i));
        }

        inCipher.close();
        inLeng.close();
        inPeriod.close();
        out.close();
        outPeriodTable.close();
    }

    private List<String> nextStep(Integer pos, String s, List<String> array, List<String> ans) {

        List<String> next = new ArrayList<>();
        if (pos < array.get(0).length()) {
            for (int i = 0; i < array.size(); i++) {
                next.add(String.valueOf(array.get(i).charAt(pos)));
            }

            for (int i = 0; i < next.size(); i++) {
                s += next.get(i);
                nextStep(pos + 1, s, array, ans);
                s = s.substring(0, s.length() - 1);
            }
        }
        if (next.size() == 0) {
            ans.add(s);
        }

        return ans;
    }
}
