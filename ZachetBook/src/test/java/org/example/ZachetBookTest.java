package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;


class ZachetBookTest {

    @Test
    void testIsExcellentStudent_true() {
        ZachetBook student = new ZachetBook("Иванов", "Иван", "Иванович", 2, 101);
        student.sessions.add(student.new Session(1, "Математика", "экзамен", "10"));
        student.sessions.add(student.new Session(1, "Физика", "зачет", "сдан"));

        assertTrue(ZachetBook.isExcellentStudent(student), "Студент должен считаться отличником");
    }

    @Test
    void testIsExcellentStudent_false() {
        ZachetBook student = new ZachetBook("Петров", "Петр", "Петрович", 2, 101);
        student.sessions.add(student.new Session(1, "Математика", "экзамен", "8"));
        student.sessions.add(student.new Session(1, "Физика", "зачет", "сдан"));

        assertFalse(ZachetBook.isExcellentStudent(student), "Студент не должен считаться отличником");
    }

    @Test
    void testSessionCreation() {
        ZachetBook student = new ZachetBook("Сидоров", "Сидор", "Сидорович", 3, 202);
        ZachetBook.Session session = student.new Session(2, "Химия", "экзамен", "9");

        assertEquals(2, session.sessionNumber);
        assertEquals("Химия", session.subjectName);
        assertEquals("экзамен", session.creditType);
        assertEquals("9", session.grade);
    }
}
