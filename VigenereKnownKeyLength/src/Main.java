import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().start();
    }
    public void start() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Вычисление таблицы частот языка открытых сообщений");
        System.out.println("2 - Вычисление ключа шифра Виженера при известной длине ключа");
        System.out.println("3 - Вычисление таблицы наиболее частых слов в языке открытых сообщений");
        System.out.println("4 - Вычисление ключа шифра Виженера при известной длине ключа");

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
