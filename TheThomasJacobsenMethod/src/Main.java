import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().start();
    }

    private void start() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Вычисление эталонной матрицы частот биграмм языка открытых сообщений");
        System.out.println("2 - Вычисление ключа");

        int meth = sc.nextInt();
        switch (meth) {
            case 1: {
                new Matrix().matrix();
                break;
            }
            case 2: {
                new Key().key();
                break;
            }
        }
    }
}
