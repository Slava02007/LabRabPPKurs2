package org.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testCountWordsInText_SimpleCount() {

        List<String> text = Arrays.asList("Привет мир", "Мир прекрасен");
        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("привет", 0);
        wordMap.put("мир", 0);
        wordMap.put("прекрасен", 0);

        Main.countWordsInText(text, wordMap);

        assertEquals(1, wordMap.get("привет"));
        assertEquals(2, wordMap.get("мир"));
        assertEquals(1, wordMap.get("прекрасен"));
    }

    @Test
    void testCountWordsInText_CaseAndPunctuation() {

        List<String> text = Arrays.asList("Яблоко, яблоко! ЯБЛОКО...", "апельсин и яблоко");
        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("яблоко", 0);
        wordMap.put("апельсин", 0);

        Main.countWordsInText(text, wordMap);

        assertEquals(4, wordMap.get("яблоко"));
        assertEquals(1, wordMap.get("апельсин"));
    }


    @Test
    void testCountWordsInText_WordNotInMap() {

        List<String> text = Arrays.asList("Это слово которого нет в карте");
        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("слово", 0);

        Main.countWordsInText(text, wordMap);

        assertEquals(1, wordMap.get("слово"));
        assertFalse(wordMap.containsKey("это"));
        assertFalse(wordMap.containsKey("которого"));
    }

    @Test
    void testPrintSortedResults_SortByFrequency() {

        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("яблоко", 5);
        wordMap.put("апельсин", 2);
        wordMap.put("банан", 8);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Main.printSortedResults(wordMap);
        System.setOut(originalOut);

        String result = outputStream.toString();

        assertTrue(result.contains("банан: 8"));
        assertTrue(result.contains("яблоко: 5"));
        assertTrue(result.contains("апельсин: 2"));

        int bananaIndex = result.indexOf("банан: 8");
        int appleIndex = result.indexOf("яблоко: 5");
        int orangeIndex = result.indexOf("апельсин: 2");

        assertTrue(bananaIndex < appleIndex);
        assertTrue(appleIndex < orangeIndex);
    }


    @Test
    void testPrintSortedResults_EmptyMap() {

        Map<String, Integer> wordMap = new HashMap<>();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Main.printSortedResults(wordMap);
        System.setOut(originalOut);

        String result = outputStream.toString();

        assertEquals("", result.trim()); // Не должно быть вывода
    }

    @Test
    void testPrintSortedResults_SingleWord() {

        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("тест", 1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Main.printSortedResults(wordMap);
        System.setOut(originalOut);

        String result = outputStream.toString();

        assertTrue(result.contains("тест: 1"));
    }
}