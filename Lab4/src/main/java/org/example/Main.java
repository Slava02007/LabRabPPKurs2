package org.example;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> map = new HashMap<>();
        List<String> text = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader("D:/Book.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.add(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }


        System.out.println("Введите слова для поиска:");
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.isEmpty()) {
                break;
            }
            if (map.containsKey(word)) {
                continue;
            }
            map.put(word, 0);
        }
        scanner.close();

        countWordsInText(text, map);
        printSortedResults(map);
    }


    public static void countWordsInText(List<String> text, Map<String, Integer> wordMap) {
        for (String line : text) {
            String[] wordsInLine = line.split("[\\s\\p{Punct}]+");

            for (String word : wordsInLine) {
                if (!word.isEmpty()) {
                    String lowerWord = word.toLowerCase();
                    if (wordMap.containsKey(lowerWord)) {
                        wordMap.put(lowerWord, wordMap.get(lowerWord) + 1);
                    }
                }
            }
        }
    }


    public static void printSortedResults(Map<String, Integer> wordMap) {
        wordMap.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<String, Integer> e) -> e.getValue())
                        .reversed()
                        .thenComparing(Map.Entry::getKey))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}