import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Task3 {

    public void task3() throws IOException {

        BufferedReader inTable = new BufferedReader(new FileReader("task3_table.txt"));

        List<String> table = new ArrayList<>();
        String s = inTable.readLine();
        while (s != null) {
            table.add(s);
            s = inTable.readLine();
        }
        for (int i = 0; i < table.size(); i++) {
            while (table.get(i).length() != table.size()) {
                table.set(i, table.get(i) + " ");
            }
        }

        tree(table);

        inTable.close();
    }

    private void tree(List<String> table) throws FileNotFoundException {

        PrintWriter out = new PrintWriter("task3_keys.txt");

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            numbers.add(0);
        }
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.size(); j++) {
                if (table.get(j).charAt(i) == ' ') {
                    numbers.set(i, numbers.get(i) + 1);
                }
            }
        }
        int minNumber = numbers.get(0);
        List<Integer> numbersIndex = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == minNumber) {
                numbersIndex.add(i);
            } else if (numbers.get(i) < minNumber) {
                numbersIndex.clear();
                numbersIndex.add(i);
                minNumber = numbers.get(i);
            }
        }
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < numbersIndex.size(); i++) {
            List<Boolean> used = new ArrayList<>();
            for (int j = 0; j < table.size(); j++) {
                used.add(false);
            }
            used.set(numbersIndex.get(i), true);
            String s = String.valueOf(numbersIndex.get(i) + 1);
            nextStep(numbersIndex.get(i), table, s, used, ans);
        }

        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i).length() == table.size()) {
                out.println(ans.get(i));
            }
        }

        out.close();
    }

    private List<String> nextStep(Integer vert, List<String> table, String s, List<Boolean> used, List<String> ans) {
        List<Integer> next = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            if (!used.get(i) && table.get(vert).charAt(i) == ' ') {
                next.add(i);
            }
        }
        for (int i = 0; i < next.size(); i++) {
            s += String.valueOf(next.get(i) + 1);
            used.set(next.get(i), true);
            nextStep(next.get(i), table, s, used, ans);
            s = s.substring(0, s.length() - 1);
            used.set(next.get(i), false);
        }
        if (next.size() == 0) {
            ans.add(s);
        }

        return ans;
    }
}
