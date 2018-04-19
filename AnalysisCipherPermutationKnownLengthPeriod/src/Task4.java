import java.io.*;

public class Task4 {

    public void task4() throws IOException {

        BufferedReader inKeys = new BufferedReader(new FileReader("task4_keys.txt"));

        int tmp = 1;
        while (true) {
            String fl = "task4_output" + Integer.toString(tmp) + ".txt";
            File file = new File(fl);
            if (file.exists()) {
                file.delete();
                tmp++;
            } else
                break;
        }

        int k = 1;
        String key = inKeys.readLine();
        while (key != null){

            BufferedReader inCipher = new BufferedReader(new FileReader("task4_cipher.txt"));
            String name = "task4_output" + String.valueOf(k) + ".txt";
            PrintWriter out = new PrintWriter(new FileWriter(name));

            StringBuilder cipher = new StringBuilder();
            String c = inCipher.readLine();
            while (c != null){
                cipher.append(c);
                c = inCipher.readLine();
            }
            c = cipher.toString();

            for (int i = 0; i < cipher.length() - key.length(); i += key.length()) {
                String str = c.substring(i, i + key.length());
                for (int j = 0; j < str.length(); j++) {
                    out.print(str.charAt(Integer.parseInt(String.valueOf(key.charAt(j % key.length()))) - 1));
                }
            }

            key = inKeys.readLine();
            k++;

            inCipher.close();
            out.close();
        }
        inKeys.close();
    }
}
