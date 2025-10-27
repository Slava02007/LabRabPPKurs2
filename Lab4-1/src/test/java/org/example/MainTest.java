package org.example;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;



public class MainTest {

    private static final String FILE1 = "f1.txt";
    private static final String FILE2 = "f2.txt";
    private static final String RESULT = "out.txt";

    @BeforeAll
    static void setup() throws IOException {

        new File("src/test/java/resources").mkdirs();

        try (PrintWriter out = new PrintWriter(FILE1)) {
            out.println("101 Иванов 1 4,5");
            out.println("102 Петров 1 3,8");
            out.println("103 Сидоров 2 4,1");
        }

        try (PrintWriter out = new PrintWriter(FILE2)) {
            out.println("102 Петров 1 3,8");
            out.println("104 Смирнов 3 4,7");
        }
    }

    @AfterAll
    static void cleanup() {
        new File(FILE1).delete();
        new File(FILE2).delete();
        new File(RESULT).delete();
    }

    @Test
    void testUnion() throws Exception {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>();
        result.addAll(list1);
        result.addAll(list2);

        Main.writeStudents(RESULT, result);

        List<Student> res = Main.readStudents(RESULT);
        assertEquals(4, res.size(), "Объединение должно содержать 4 записи");
    }

    @Test
    void testIntersection() throws Exception {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>();
        for (Student s : list1)
            if (list2.contains(s))
                result.add(s);

        Main.writeStudents(RESULT, result);

        List<Student> res = Main.readStudents(RESULT);
        assertEquals(1, res.size(), "Пересечение должно содержать 1 запись");
        assertEquals(102, res.get(0).num, "Общий студент — с номером 102");
    }

    @Test
    void testDifference() throws Exception {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>();
        for (Student s : list1)
            if (!list2.contains(s))
                result.add(s);

        Main.writeStudents(RESULT, result);

        List<Student> res = Main.readStudents(RESULT);
        assertEquals(2, res.size(), "Разность должна содержать 2 записи");
        assertTrue(res.stream().anyMatch(s -> s.num == 101));
        assertTrue(res.stream().anyMatch(s -> s.num == 103));
    }
}
