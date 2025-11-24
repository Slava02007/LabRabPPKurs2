package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String fileinput = "input.txt";
        String fileoutput = "output.txt";
        Scanner sc = new Scanner(System.in);

        try {
            ArrayList<String> text = FileInOut.readFile(fileinput);
            System.out.println("Информация прочитана из файла.");
            String fullText = String.join(" ", text);
            fullText = fullText.trim();
            System.out.println("Введите ширину строки:");
            int size = sc.nextInt();
            if (size <= 0) {
                System.out.println("Ошибка: ширина строки должна быть положительным числом");
                return;
            }
            ArrayList<String> formattedText = PerenosText.processText(fullText, size);
            FileInOut.writeFile(fileoutput, formattedText);
            System.out.println("Текст отформатирован и записан в файл " + fileoutput);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}