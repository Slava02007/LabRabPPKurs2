package org.example;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void testSimpleSort() {
        String input = "яблоко дом аист";
        String expected = "дом аист яблоко";
        assertEquals(expected, Main.sortWordsByLength(input));
    }

    @org.junit.jupiter.api.Test
    void testWithExtraSpacesAndCommas() {
        String input = "  кот, собака. слон ";
        String expected = "кот слон собака";
        assertEquals(expected, Main.sortWordsByLength(input));
    }

    @org.junit.jupiter.api.Test
    void testSingleWord() {
        String input = "один";
        assertEquals("один", Main.sortWordsByLength(input));
    }

    @org.junit.jupiter.api.Test
    void testEmptyString() {
        assertEquals("", Main.sortWordsByLength(""));
    }

    @org.junit.jupiter.api.Test
    void testNullInput() {
        assertEquals("", Main.sortWordsByLength(null));
    }

    @org.junit.jupiter.api.Test
    void testWordsWithSameLength() {
        String input = "кот дом";
        // порядок сохраняется, так как оба слова длиной 3
        String expected = "кот дом";
        assertEquals(expected, Main.sortWordsByLength(input));
    }
}
