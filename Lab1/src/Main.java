import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x = 0;
        int k = 0;


        while (true) {
            try {
                System.out.print("Введите x (-1 < x < 1): ");
                x = scanner.nextDouble();

                if (x > -1 && x < 1) break;
                else System.out.println("Ошибка: x должен быть в диапазоне (-1, 1)");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число!");
                scanner.next();
            }
        }


        while (true) {
            try {
                System.out.print("Введите k (натуральное число): ");
                k = scanner.nextInt();

                if (k > 0) break;
                else System.out.println("Ошибка: k должен быть натуральным числом (> 0)");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число!");
                scanner.next();
            }
        }

        double epsilon = Math.pow(10, -k);
        double taylorResult = ArcsinCalculator.calculate(x, epsilon);
        double exactResult = Math.asin(x);

        System.out.println("\nРезультаты:");
        System.out.printf("Ряд Тейлора: %.3f%n", taylorResult);
        System.out.printf("Arcsin:     %.3f%n", exactResult);
        System.out.printf("Разница:    %.3f%n", Math.abs(taylorResult - exactResult));

        scanner.close();
    }
}




