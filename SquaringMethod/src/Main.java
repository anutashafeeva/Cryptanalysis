import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите натуральное число n");
        BigInteger n = scanner.nextBigInteger();
        System.out.println("Введите коэффициент близости k");
        BigInteger k = scanner.nextBigInteger();
        System.out.println("Введите число итераций l");
        int l = scanner.nextInt();

        BigInteger p = method(n, k, l);
        if (p.equals(BigInteger.ONE.negate()))
            System.out.println("Не удалось найти делитель");
        else
            System.out.println("Делитель числа " + n + " = " + p);
    }

    private BigInteger method(BigInteger n, BigInteger k, int l){
        Scanner scanner = new Scanner(System.in);
        BigInteger s = BigInteger.valueOf((long) Math.sqrt(n.multiply(k).doubleValue()));
        while (true){
            for (int i = 0; i < l; i++) {
                s = s.add(BigInteger.ONE);
                if (!isSquare(s.pow(2).subtract(k.multiply(n))).equals(BigInteger.ONE.negate())){
                    return gcd(k.multiply(n), s.subtract(isSquare(s.pow(2).subtract(k.multiply(n)))));
                }
            }
            System.out.println("Прошло " + l + " итераций, продолжить дальше (Y/N)?");
            String YN = scanner.nextLine();
            if (YN.equals("N"))
                break;
        }
        return BigInteger.ONE.negate();
    }

    private BigInteger isSquare(BigInteger number){
        BigInteger sqrtNumber = BigInteger.valueOf((long) Math.sqrt(number.doubleValue()));
        if (sqrtNumber.pow(2).equals(number))
            return sqrtNumber;
        return BigInteger.ONE.negate();
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        while (true) {
            if (a.compareTo(b) == 1) {
                if (!a.mod(b).equals(BigInteger.ZERO))
                    a = a.mod(b);
                else return b;
            } else {
                if (!b.mod(a).equals(BigInteger.ZERO))
                    b = b.mod(a);
                else return a;
            }
        }
    }
}
