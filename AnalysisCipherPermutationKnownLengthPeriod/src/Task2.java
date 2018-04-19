import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Task2 {

    public void task2() throws IOException {

        BufferedReader inCipher = new BufferedReader(new FileReader("task2_cipher.txt"));
        BufferedReader inLength = new BufferedReader(new FileReader("task2_length.txt"));
        BufferedReader inDigrams = new BufferedReader(new FileReader("task2_digrams.txt"));
        PrintWriter outTable = new PrintWriter("task2_textTable.txt");
        PrintWriter out = new PrintWriter("task2_table.txt");

        int k = Integer.parseInt(inLength.readLine());

        StringBuilder cipher = new StringBuilder();
        String s = inCipher.readLine();
        while (s != null) {
            s = s.toLowerCase();
            cipher.append(s);
            s = inCipher.readLine();
        }

        List<String> textTable = new ArrayList<>();
        for (int i = 0; i < cipher.length() - k; i += k) {
            textTable.add(cipher.substring(i, i + k));
        }
        for (int i = 0; i < textTable.size(); i++) {
            outTable.println(textTable.get(i));
        }

        HashSet<String> digr = new HashSet<>();
        s = inDigrams.readLine();
        while (s != null) {
            digr.add(s);
            s = inDigrams.readLine();
        }

        List<String> ansTable = new ArrayList<>();
        for (int j1 = 0; j1 < k; j1++) {
            StringBuilder ans = new StringBuilder();
            for (int j2 = 0; j2 < k; j2++) {
                if (j1 == j2) {
                    ans.append("X");
                } else {
                    for (int i = 0; i < textTable.size(); i++) {
                        if (Character.isLetter(textTable.get(i).charAt(j1)) && Character.isLetter(textTable.get(i).charAt(j2))) {
                            if (digr.contains(String.valueOf(textTable.get(i).charAt(j1)) + String.valueOf(textTable.get(i).charAt(j2)))) {
                                ans.append("X");
                                break;
                            }
                        }
                    }
                }
                if (ans.length() != j2 + 1){
                    ans.append(" ");
                }
            }
            ansTable.add(ans.toString());
        }
        for (int i = 0; i < ansTable.size(); i++) {
            out.println(ansTable.get(i));
        }

        inCipher.close();
        inDigrams.close();
        inLength.close();
        outTable.close();
        out.close();
    }
}
