import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().start();
    }
    public void start() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Вычисление множества запретных биграмм языка открытых сообщений");
        System.out.println("2 - Построение вспомогательной таблицы для анализа шифра перестановки при известной длине периода");
        System.out.println("3 - Построение ориентированного леса возможных перестановок");
        System.out.println("4 - Перебор ключей по ориентированному лесу возможных перестановок");

        int parameter = scanner.nextInt();
        switch (parameter){
            case 1:{
                new Task1().task1();
                break;
            }
            case 2:{
                new Task2().task2();
                break;
            }
            case 3:{
                new Task3().task3();
                break;
            }
            case 4:{
                new Task4().task4();
                break;
            }
        }
    }
}
