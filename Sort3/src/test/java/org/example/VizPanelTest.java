package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class VizPanelTest {

   private void testSortingAlgorithm(String algoName, int[] input, int[] expected) {
        VizPanel panel = new VizPanel(input);

        switch (algoName) {
            case "bubble":
                panel.bubbleSort();
                break;
            case "gnome":
                panel.gnomeSort();
                break;
            case "cocktail":
                panel.cocktailSort();
                break;
        }

        int[] actual = panel.getArr();
        Assertions.assertArrayEquals(expected, actual, "Сортировка " + algoName + " сработала неправильно");
    }

    @Test
    public void testBubbleSort() {
        int[] input = {5, 1, 4, 2, 8};
        int[] expected = {1, 2, 4, 5, 8};
        testSortingAlgorithm("bubble", input, expected);
    }

    @Test
    public void testGnomeSort() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        testSortingAlgorithm("gnome", input, expected);
    }

    @Test
    public void testCocktailSort() {
        int[] input = {3, -1, 3, 0, 10, 5};
        int[] expected = {-1, 0, 3, 3, 5, 10};
        testSortingAlgorithm("cocktail", input, expected);
    }

    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        testSortingAlgorithm("bubble", input, expected);
    }

    @Test
    public void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        testSortingAlgorithm("cocktail", input, expected);
    }
}