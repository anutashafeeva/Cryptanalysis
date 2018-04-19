import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Task1 {

    public void task() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("task1_input.txt"));
        PrintWriter outAlph = new PrintWriter("task1_alph.txt");
        PrintWriter outPeriodAlph = new PrintWriter("task1_periodicity.txt");
        PrintWriter outH0 = new PrintWriter("task1_hypothesis.txt");

        TreeSet<Character> alph = new TreeSet<>();
        StringBuilder text = new StringBuilder();
        String s = in.readLine();
        while (s != null) {
            s = s.toLowerCase();
            text.append(s);
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i))) {
                    alph.add(s.charAt(i));
                }
            }
            s = in.readLine();
        }
        for (Character character : alph) {
            outAlph.print(character);
        }

        Long allCharacter = 0L;
        HashMap<String, Integer> charQuantity = new HashMap<>();
        for (Character character : alph) {
            charQuantity.put(String.valueOf(character), 0);
        }
        for (int i = 0; i < text.length(); i++) {
            if (alph.contains(text.charAt(i))) {
                allCharacter++;
                int number = charQuantity.get(String.valueOf(text.charAt(i)));
                charQuantity.replace(String.valueOf(text.charAt(i)), number + 1);
            }
        }


        List<Pair<Double, String>> periodicity = new ArrayList<>();
        for (String character : charQuantity.keySet()) {
            periodicity.add(new Pair<>(((double) charQuantity.get(character)), character));
        }

        Collections.sort(periodicity, Comparator.comparing(Pair::getKey));

        for (int i = periodicity.size() - 1; i >= 0; i--) {
            outPeriodAlph.println(periodicity.get(i).getValue() + " - " + (periodicity.get(i).getKey() / allCharacter));
        }


        List<Double> hypothesis = new ArrayList<>();
        for (int i = 0; i < periodicity.size(); i++) {
            double hyp = 0;
            for (int j = 0; j < periodicity.size(); j++) {
                for (int k = 0; k < periodicity.size(); k++) {
                    if ((j - k + periodicity.size()) % periodicity.size() == i) {
                        hyp += (periodicity.get(j).getKey() * periodicity.get(k).getKey()) / (allCharacter * allCharacter);
                    }
                }
            }
            hypothesis.add(hyp);
        }

        for (int i = 0; i < hypothesis.size(); i++) {
            outH0.println("P_" + i + " = " + hypothesis.get(i));
        }


        in.close();
        outAlph.close();
        outPeriodAlph.close();
        outH0.close();
    }
}
