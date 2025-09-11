public class ArcsinCalculator {
    public static double calculate(double x, double epsilon) {
        double sum = x;
        double term = x;
        int n = 1;

        while (Math.abs(term) >= epsilon) {
            double coefficient = (double) (2 * n - 1) * (2 * n - 1) / (2 * n * (2 * n + 1));
            term = term * coefficient * x * x;
            sum += term;
            n++;

            if (n > 10000) {
                System.out.println("Внимание: достигнут предел итераций (10000).");
                break;
            }
        }

        return sum;
    }
}

