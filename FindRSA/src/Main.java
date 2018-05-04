import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        new Main().start();
    }

    private void start() throws IOException, NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Вычисление параметров системы RSA");
        System.out.println("2 - Разложение на множители по известному значению функции Эйлера");
        System.out.println("3 - Разложение на множители по известным показателям RSA");
        int parameter = scanner.nextInt();

        switch (parameter){
            case 1:{
                SystemParameters sp = new SystemParameters();
                sp.systemParameters();
                break;
            }
            case  2:{
                ExpandingKnownEulerFunc exp = new ExpandingKnownEulerFunc();
                exp.expandingKnownEulerFunc();
                break;
            }
            case 3:{
                FactorizationKnownRSA fact = new FactorizationKnownRSA();
                fact.factorizationKnownRSA();
                break;
            }
            default: return;
        }
    }
}
