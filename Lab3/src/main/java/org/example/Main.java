package org.example;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> stringList = new ArrayList<>();


        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }
            stringList.add(line);
        }
        scanner.close();

        System.out.println("--------------------------");
        System.out.println("Первоначальные строки:");
        for (String line : stringList) {
            System.out.println(line);
        }

        System.out.println("--------------------------");
        System.out.println("Строки после сортировки:");
        for (String line : stringList) {
            String result = sortWordsByLength(line);
            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }
    }


    public static String sortWordsByLength(String line) {
        if (line == null || line.trim().isEmpty()) {
            return "";
        }

        String cleaned = line.replaceAll("[., ]+", " ")
                .replaceAll("\\s+", " ")
                .trim();

        if (cleaned.isEmpty()) {
            return "";
        }

        String[] words = cleaned.split(" ");
        Arrays.sort(words, Comparator.comparingInt(String::length));
        return String.join(" ", words);
    }
}
