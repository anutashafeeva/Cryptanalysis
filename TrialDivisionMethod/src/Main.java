import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        /*PrintWriter out = new PrintWriter("task1_prime.txt");
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(BigInteger.valueOf(10000)) != 1; i = i.add(BigInteger.ONE)) {
            if (isPrime(i)){
                out.println(i);
            }
        }
        out.close();
        */

        new Main().start();
    }

    public void start() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Создание базы данных из произведений простых чисел");
        System.out.println("2 - Метод пробного деления");

        int method = scanner.nextInt();
        switch (method) {
            case 1: {
                CreateBase createBase = new CreateBase();
                createBase.createBase();
                break;
            }
            case 2: {
                Method m = new Method();
                m.method();
                break;
            }
            default:
                return;
        }
    }

    public static boolean isPrime(BigInteger n) {
        if (n.equals(BigInteger.valueOf(2))) return true;
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) return false;
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(n.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)) != 1; i = i.add(BigInteger.valueOf(2))) {
            if (n.mod(i).equals(BigInteger.ZERO))
                return false;
        }
        return true;
    }
}
