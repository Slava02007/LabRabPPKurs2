package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] mas = new int[20];
        Random rand = new Random();

        for (int i = 0; i < mas.length; i++) {
            mas[i] = rand.nextInt(15) + 1;
        }

        System.out.println("1. Исходный массив:");
        printArray(mas);

        System.out.println("\n2. Повторяющиеся элементы:");
        findDuplicates(mas);

        System.out.println("\n3. Массив без дубликатов:");
        removeDuplicates(mas);

        System.out.println("\n4. Элемент, который встречается чаще всего:");
        findMaxFrequency(mas);
    }


    public static void printArray(int[] a) {
        for (int x : a) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public static void findDuplicates(int[] a) {
        HashMap<Integer, Integer> map = getCounts(a);
        for (Integer key : map.keySet()) {
            if (map.get(key) > 1) {
                System.out.print(key + " ");
            }
        }
        System.out.println();
    }

    public static void removeDuplicates(int[] a) {
        HashMap<Integer, Integer> map = getCounts(a);
        int[] newMas = new int[map.size()];
        int i = 0;
        for (Integer key : map.keySet()) {
            newMas[i] = key;
            i++;
        }
        printArray(newMas);
    }

    public static void findMaxFrequency(int[] a) {
        HashMap<Integer, Integer> map = getCounts(a);
        int maxCount = 0;
        int resultNum = 0;

        for (Integer key : map.keySet()) {
            if (map.get(key) > maxCount) {
                maxCount = map.get(key);
                resultNum = key;
            }
        }
        System.out.println("Число " + resultNum + " встретилось " + maxCount + " раз(а).");
    }

    public static HashMap<Integer, Integer> getCounts(int[] a) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : a) {
            if (map.containsKey(x)) {
                map.put(x, map.get(x) + 1);
            } else {
                map.put(x, 1);
            }
        }
        return map;
    }
}