import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class Method {

    public void methodSimpson() throws IOException {

        BufferedReader inCipher = new BufferedReader(new FileReader("cipher.txt"));
        BufferedReader inLength = new BufferedReader(new FileReader("length.txt"));
        BufferedReader inPeriodicity = new BufferedReader(new FileReader("periodicity.txt"));
        BufferedReader inAlph = new BufferedReader(new FileReader("alph.txt"));

        StringBuilder cipher = new StringBuilder();
        String c = inCipher.readLine();
        while (c != null) {
            cipher.append(c);
            c = inCipher.readLine();
        }

        int d = Integer.parseInt(inLength.readLine());

        String alph = inAlph.readLine();

        TreeMap<String, Double> periodicity = new TreeMap<>();
        String p = inPeriodicity.readLine();
        while (p != null) {
            periodicity.put(p.substring(0, p.indexOf('-') - 1), Double.parseDouble(p.substring(p.indexOf('-') + 1)));
            p = inPeriodicity.readLine();
        }

        List<String> yi = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = i; j < cipher.length(); j += d) {
                s.append(cipher.charAt(j));
            }
            yi.add(s.toString());
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i < d; i++) {
            List<Double> ind = new ArrayList<>();
            for (int j = 0; j < alph.length(); j++) {
                StringBuilder newY = new StringBuilder();
                for (int k = 0; k < yi.get(i).length(); k++) {
                    if (Character.isLetter(yi.get(i).charAt(k)))
                        newY.append(alph.charAt((alph.indexOf(yi.get(i).charAt(k)) - j + alph.length()) % alph.length()));
                    else newY.append(yi.get(i).charAt(k));
                }
                ind.add(new Index().index(yi.get(0), newY.toString()));
            }
            ans.add(numberOfMax(ind));
        }

        TreeMap<String, Integer> periodY0 = new TreeMap<>();
        List<Pair<Integer, String>> newPeriodY0 = new ArrayList<>();
        for (int i = 0; i < yi.get(0).length(); i++) {
            if (Character.isLetter(yi.get(0).charAt(i))) {
                if (!periodY0.containsKey(String.valueOf(yi.get(0).charAt(i))))
                    periodY0.put(String.valueOf(yi.get(0).charAt(i)), 1);
                else
                    periodY0.put(String.valueOf(yi.get(0).charAt(i)), periodY0.get(String.valueOf(yi.get(0).charAt(i))) + 1);
            }
        }
        for (String s : periodY0.keySet()) {
            newPeriodY0.add(new Pair<>(periodY0.get(s), s));
        }
        newPeriodY0.sort(Comparator.comparingDouble(Pair::getKey));

        String s0 = String.valueOf(alph.charAt((alph.indexOf(newPeriodY0.get(newPeriodY0.size() - 1).getValue()) + alph.length() - alph.indexOf('о')) % alph.length()));
        System.out.print(s0);
        for (int j = 0; j < ans.size(); j++) {
            System.out.print(alph.charAt((ans.get(j) + alph.indexOf(s0) + alph.length()) % alph.length()));
        }
    }

    int numberOfMax(List<Double> ind) {
        int numb = 0;
        Double max = ind.get(0);
        for (int i = 1; i < ind.size(); i++) {
            if (ind.get(i) > max) {
                max = ind.get(i);
                numb = i;
            }
        }
        return numb;
    }
}
