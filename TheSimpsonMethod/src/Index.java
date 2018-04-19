import javafx.util.Pair;

import java.util.TreeMap;

public class Index {

    public Double index(String s1, String s2) {

        TreeMap<String, Pair<Double, Double>> period = new TreeMap<>();

        int lengthS1 = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (Character.isLetter(s1.charAt(i))) {
                lengthS1++;
                if (!period.containsKey(String.valueOf(s1.charAt(i))))
                    period.put(String.valueOf(s1.charAt(i)), new Pair<>(1.0, 0.D));
                else
                    period.put(String.valueOf(s1.charAt(i)), new Pair<>(period.get(String.valueOf(s1.charAt(i))).getKey() + 1.0, 0.D));
            }
        }
        int lengthS2 = 0;
        for (int i = 0; i < s2.length(); i++) {
            if (Character.isLetter(s2.charAt(i))) {
                lengthS2++;
                if (!period.containsKey(String.valueOf(s2.charAt(i))))
                    period.put(String.valueOf(s2.charAt(i)), new Pair<>(0.D, 1.0));
                else
                    period.put(String.valueOf(s2.charAt(i)), new Pair<>(period.get(String.valueOf(s2.charAt(i))).getKey(), period.get(String.valueOf(s2.charAt(i))).getValue() + 1.0));
            }
        }

        Double res = 0.D;
        for (String c : period.keySet()) {
            res += ((period.get(c).getKey() * period.get(c).getValue()) / (lengthS1 * lengthS2));
        }

        return res;
    }
}
