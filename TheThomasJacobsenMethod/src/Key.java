import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.lang.Math.random;

public class Key {

    public void key() throws IOException {

        BufferedReader inCipher = new BufferedReader(new FileReader("task2_cipher.txt"));
        BufferedReader inAlph = new BufferedReader(new FileReader("task2_alph.txt"));
        BufferedReader inLength = new BufferedReader(new FileReader("task2_length.txt"));
        BufferedReader inMatrix = new BufferedReader(new FileReader("task2_matrix.txt"));

        String c = inCipher.readLine();
        StringBuilder cipher = new StringBuilder();
        while (c != null) {
            cipher.append(c);
            c = inCipher.readLine();
        }

        int d = Integer.parseInt(inLength.readLine());

        String alph = inAlph.readLine();

        TreeMap<String, Double> etalonMatrix = new TreeMap<>();
        String m = inMatrix.readLine();
        while (m != null) {
            etalonMatrix.put(m.substring(0, m.indexOf('-') - 1), Double.parseDouble(m.substring(m.indexOf('-') + 1)));
            m = inMatrix.readLine();
        }

        List<String> table = new ArrayList<>();
        table.add(alph);
        for (int i = 0; i < alph.length() - 1; i++) {
            String s = alph.substring(i + 1) + alph.substring(0, i + 1);
            table.add(s);
        }

        String k = "";
        String k0 = "";
        for (int i = 0; i < d; i++) {
            k0 += String.valueOf(alph.charAt((int) (random() * alph.length())));
        }
        k = k0;

        while (true) {
            StringBuilder kY = cipher(cipher, k, alph, table);
            TreeMap<String, Double> matrD = matrD(kY, alph);

            double w = w(etalonMatrix, matrD);

            double min = w;
            for (int i = 0; i < d; i++) {
                int ind = alph.indexOf(k.charAt(i));
                for (int j = 0; j < alph.length(); j++) {
                    k = k.substring(0, i) + alph.charAt(j) + k.substring(i + 1);
                    StringBuilder newkY = cipher(cipher, k, alph, table);
                    TreeMap<String, Double> newMatrD = matrD(newkY, alph);
                    double newW = w(etalonMatrix, newMatrD);
                    if (newW < min) {
                        min = newW;
                        ind = j;
                    }
                }
                k = k.substring(0, i) + alph.charAt(ind) + k.substring(i + 1);
            }

            if (!k0.equals(k))
                k0 = k;
            else break;
        }

        System.out.println(k);
    }

    private StringBuilder cipher(StringBuilder y, String key, String alph, List<String> table) {
        StringBuilder ansCipher = new StringBuilder();

        int posKey = 0;
        for (int i = 0; i < y.length(); i++) {
            if (Character.isLetter(y.charAt(i))) {
                int posI = alph.indexOf(key.charAt(posKey));
                String x = table.get(posI);
                int posJ = x.indexOf(y.charAt(i));
                ansCipher.append(table.get(0).charAt(posJ));
                posKey = (posKey + 1) % key.length();
            } else
                ansCipher.append(y.charAt(i));
        }

        return ansCipher;
    }

    private TreeMap<String, Double> matrD(StringBuilder kY, String alph) {

        TreeMap<String, Double> matr = new TreeMap<>();
        for (int i = 0; i < alph.length(); i++) {
            for (int j = 0; j < alph.length(); j++) {
                String newS = String.valueOf(alph.charAt(i)) + String.valueOf(alph.charAt(j));
                matr.put(newS, 0.D);
            }
        }
        int allText = 0;
        for (int i = 0; i < kY.length() - 1; i++) {
            if (Character.isLetter(kY.charAt(i)) && Character.isLetter(kY.charAt(i + 1))) {
                allText++;
                String newS = String.valueOf(kY.charAt(i)) + String.valueOf(kY.charAt(i + 1));
                matr.put(newS, matr.get(newS) + 1);
            }
        }
        for (String s : matr.keySet()) {
            matr.put(s, matr.get(s) / allText);
        }

        return matr;
    }

    private double w(TreeMap<String, Double> e, TreeMap<String, Double> d) {
        double w = 0.D;
        for (String s : d.keySet()) {
            w += Math.abs(d.get(s) - e.get(s));
        }
        return w;
    }
}
