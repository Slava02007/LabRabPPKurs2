package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
            Scanner scanner=new Scanner(System.in);
            Map<String,Integer> map=new HashMap<>();
            List<String> text=new ArrayList<>();

            System.out.println("Введите текст:");
            while(true){
                String line= scanner.nextLine().trim();
                if(line.isEmpty()){
                    break;
                }
                text.add(line);
            }

        System.out.println("Введите слова для поиска:");
        while(true){
            String word= scanner.nextLine().trim();
            if(word.isEmpty()){
                break;
            }
            if(map.containsKey(word)){
                continue;
            }
            map.put(word,0);
        }
        scanner.close();

        for (String line:text){
            String[] wordsInLine=line.split("[\\s\\p{Punct}]+");

                for(String word:wordsInLine) {
                    if (!word.isEmpty()) {
                        String lowerWord = word.toLowerCase();
                        if (map.containsKey(lowerWord)) {
                            map.put(lowerWord, map.get(lowerWord) + 1);
                        }
                    }
                }

        }

        map.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<String,Integer> e) -> e.getValue())
                        .reversed()
                        .thenComparing(Map.Entry::getKey))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

    }
}