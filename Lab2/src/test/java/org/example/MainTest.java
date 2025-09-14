package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void minElement_NormalMatrix_ReturnsMinUniqueElement() {
        int[][] matrix = {
                {5, 3, 2},
                {1, 4, 6},
                {7, 8, 9}
        };

        assertEquals(1, Main.minElement(matrix));//assertEquals(ожидаемое значение, фактический результат;
    }

    @org.junit.jupiter.api.Test
    void minElement_MinValueDuplicated_ReturnsNextMinUnique() {
        int[][] matrix = {
                {1, 1, 3},
                {1, 2, 4},
                {5, 6, 7}
        };

        assertEquals(2, Main.minElement(matrix));
    }

    @org.junit.jupiter.api.Test
    void minElement_NoUniqueElements_ThrowsException() {
        int[][] matrix = {
                {2, 2},
                {2, 2}
        };

        Exception exception = assertThrows(RuntimeException.class,
                () -> Main.minElement(matrix));//assertThrows(ОжидаемыйТипИсключения.class, ВыполняемыйКод)
        assertEquals("Нет уникальных элементов", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void minElement_EmptyMatrix_ThrowsException() {
        int[][] matrix = {};

        Exception exception = assertThrows(RuntimeException.class,
                () -> Main.minElement(matrix));
        assertEquals("Матрица пустая", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    void minElement_SingleElement_ReturnsThatElement() {
        int[][] matrix = {{5}};

        assertEquals(5, Main.minElement(matrix));
    }
}