import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main().start();
    }

    void start() throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Вычисление значений гипотезы Н(0)");
        System.out.println("2 - Вычисление значений гипотезы Н(d) с наиболее вероятной длиной периода d");

        int parameter = scanner.nextInt();
        switch (parameter) {
            case 1:
            {
                Task1 task1 = new Task1();
                task1.task();
                break;
            }
            case 2:{
                Task2 task2 = new Task2();
                task2.task();
                break;
            }
        }
    }
}
