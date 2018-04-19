import java.io.*;
import java.util.TreeMap;
import java.util.TreeSet;

public class Task1 {

    public void task1() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("task1_input.txt"));
        PrintWriter outAlph = new PrintWriter("task1_alph.txt");
        PrintWriter outDigr = new PrintWriter("task1_digrams.txt");

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

        TreeMap<String, Integer> digr = new TreeMap();
        for (Character character1 : alph) {
            for (Character character2 : alph) {
                digr.put(String.valueOf(character1) + String.valueOf(character2), 0);
            }
        }

        for (int i = 0; i < text.length() - 1; i++) {
            if (Character.isLetter(text.charAt(i)) && Character.isLetter(text.charAt(i + 1))) {
                int number = digr.get(String.valueOf(text.charAt(i)) + String.valueOf(text.charAt(i + 1)));
                digr.replace(String.valueOf(text.charAt(i)) + String.valueOf(text.charAt(i + 1)), number + 1);
            }
        }

        for (String digrams : digr.keySet()) {
            if (digr.get(digrams) == 0) {
                outDigr.println(digrams);
            }
        }

        in.close();
        outAlph.close();
        outDigr.close();
    }
}
