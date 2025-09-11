import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Введите количество строк матрицы:");
        int n = sc.nextInt();
        System.out.println("Введите количество столбцов матрицы:");
        int m = sc.nextInt();

        int[][] matrix = new int[n][m];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }


        System.out.println("\nСгенерированная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }


        System.out.println("\nМинимальный элемент в матрице который встречается один раз:");
        System.out.println(minElement(matrix));
        sc.close();
    }

    static int minElement(int matrix[][]){
        int n = matrix.length;
        int m = matrix[0].length;
        int k = 0;


        int[] Arr = new int[n * m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int p = 0;

                for (int z = 0; z < n; z++) {
                    for (int x = 0; x < m; x++) {
                        if (matrix[i][j] == matrix[z][x]) {
                            p++;
                        }
                    }
                }

                if (p == 1){
                    Arr[k] = matrix[i][j];
                    k++;
                }
            }
        }


        if (k == 0) {
            throw new RuntimeException("Нет уникальных элементов");
        }

        int min = Arr[0];
        for (int i = 1; i < k; i++) {
            if (Arr[i] < min) {
                min = Arr[i];
            }
        }

        return min;
    }
}

