import java.io.*;
import java.util.TreeMap;

public class Matrix {

    public void matrix() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("task1_input.txt"));
        BufferedReader inAlph = new BufferedReader(new FileReader("task1_alph.txt"));
        PrintWriter out = new PrintWriter("task1_output.txt");

        String alph = inAlph.readLine();
        TreeMap<String, Double> matr = new TreeMap<>();
        for (int i = 0; i < alph.length(); i++) {
            for (int j = 0; j < alph.length(); j++) {
                String newS = String.valueOf(alph.charAt(i)) + String.valueOf(alph.charAt(j));
                matr.put(newS, 0.D);
            }
        }

        int allText = 0;
        String s = in.readLine();

        while (s != null){
            s = s.toLowerCase();
            for (int i = 0; i < s.length() - 1; i++) {
                if (Character.isLetter(s.charAt(i)) && Character.isLetter(s.charAt(i+1))){
                    allText++;
                    String newS = String.valueOf(s.charAt(i)) + String.valueOf(s.charAt(i+1));
                    matr.put(newS, matr.get(newS) + 1);
                }
            }
            s = in.readLine();
        }

        for (String str: matr.keySet()) {
            out.println(str + " - " + (matr.get(str) / allText));
        }

        in.close();
        out.close();
    }
}
