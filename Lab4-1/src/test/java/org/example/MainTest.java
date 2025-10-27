package org.example;


import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String FILE1 = "test_file1.txt";
    private static final String FILE2 = "test_file2.txt";
    private static final String OUTFILE = "test_out.txt";

    @BeforeEach
    void setUp() throws IOException {
        // создаём временные файлы с тестовыми данными
        Files.writeString(Path.of(FILE1), """
                1 Ivan 101 4,5
                2 Maria 102 3,9
                3 Alex 103 4,2
                """);

        Files.writeString(Path.of(FILE2), """
                3 Alex 103 4,2
                4 Olga 104 4,8
                5 Petr 105 3,7
                """);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE1));
        Files.deleteIfExists(Path.of(FILE2));
        Files.deleteIfExists(Path.of(OUTFILE));
    }

    @Test
    void testReadStudents() throws IOException {
        List<Student> students = Main.readStudents(FILE1);
        assertEquals(3, students.size());
        assertEquals("Ivan", students.get(0).name);
    }

    @Test
    void testWriteStudents() throws IOException {
        List<Student> students = List.of(
                new Student(10, "Test", 999, 5.0)
        );
        Main.writeStudents(OUTFILE, students);

        String content = Files.readString(Path.of(OUTFILE)).trim();
        assertEquals("10 Test 999 5.0", content);
    }

    @Test
    void testUnionOperation() throws IOException {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>(list1);
        result.addAll(list2);

        assertEquals(5, result.size(), "Объединение должно содержать 5 студентов");
    }

    @Test
    void testIntersectionOperation() throws IOException {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>();
        for (Student s : list1) {
            if (list2.contains(s)) result.add(s);
        }

        assertEquals(1, result.size());
        assertTrue(result.contains(new Student(3, "Alex", 103, 4.2)));
    }

    @Test
    void testDifferenceOperation() throws IOException {
        List<Student> list1 = Main.readStudents(FILE1);
        List<Student> list2 = Main.readStudents(FILE2);

        Set<Student> result = new HashSet<>();
        for (Student s : list1) {
            if (!list2.contains(s)) result.add(s);
        }

        assertEquals(2, result.size());
        assertTrue(result.contains(new Student(1, "Ivan", 101, 4.5)));
        assertTrue(result.contains(new Student(2, "Maria", 102, 3.9)));
    }
}
