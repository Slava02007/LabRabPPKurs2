package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class PerenosTextTest {

    @Test
    void testPerenos() throws IOException {
        String text = "раз два три четыре пять шесть";
        ArrayList<String> result = PerenosText.perenos(text, 10);

        assertEquals(3, result.size());
        for (String line : result) {
            assertTrue(line.length() <= 10, "Строка превышает максимальную длину: " + line);
        }
    }

    @Test
    void testJustifyTextSimple() {
        ArrayList<String> input = new ArrayList<>(Arrays.asList(
                "короткая строка",
                "другая строка",
                "последняя"
        ));

        ArrayList<String> result = PerenosText.justifyTextSimple(input, 20);

        assertEquals(3, result.size());
        assertEquals(20, result.get(0).length());
        assertEquals(20, result.get(1).length());
        assertEquals("последняя", result.get(2));
    }

    @Test
    void testProcessText() throws IOException {
        String text = "Это тест для проверки всей цепочки обработки";
        ArrayList<String> result = PerenosText.processText(text, 25);

        assertFalse(result.isEmpty());
        for (int i = 0; i < result.size() - 1; i++) {
            assertEquals(25, result.get(i).length());
        }
    }
}