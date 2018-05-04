import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Task3 {

    public void task3() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("task3_input.txt"));
        PrintWriter outPeriodicityWords = new PrintWriter("task3_periodicity.txt");


        HashMap<String, Integer> wordQuantity = new HashMap<>();
        Long allWords = 0L;

        String s = in.readLine();
        while (s != null) {
            s = s.toLowerCase();
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isLetter(s.charAt(i)))
                    s = s.replace(s.charAt(i), ' ');
                if (i != 0 && s.charAt(i - 1) == ' ' && s.charAt(i) == ' ') {
                    s = s.substring(0, i - 1) + s.substring(i);
                    i--;
                }
            }
            String[] str = s.split(" ");
            for (int i = 0; i < str.length; i++) {
                if (!str[i].equals("") && str[i].length() > 3) {
                    allWords++;
                    if (wordQuantity.containsKey(str[i])) {
                        wordQuantity.put(str[i], wordQuantity.get(str[i]) + 1);
                    } else wordQuantity.put(str[i], 1);
                }
            }
            s = in.readLine();
        }

        List<Pair<Double, String>> ans = new ArrayList<>();
        for (String str : wordQuantity.keySet()) {
            ans.add(new Pair<>((double) wordQuantity.get(str), str));
        }
        ans.sort(Comparator.comparingDouble(Pair::getKey));
        for (int i = ans.size() - 1; i >= ans.size() / 2; i--) {
            outPeriodicityWords.println(ans.get(i).getValue() + " - " + ans.get(i).getKey() / allWords);
        }

        in.close();
        outPeriodicityWords.close();
    }
}
