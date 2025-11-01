package org.example;

import java.io.*;
import java.util.*;


public class ZachetBook {
    String lastName;
    String firstName;
    String patronymic;
    int group;
    int course;
    ArrayList<Session> sessions = new ArrayList<>();

    public ZachetBook(String lastName, String firstName, String patronymic, int course, int group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.course = course;
        this.group = group;
    }

    class Session {
        int sessionNumber;
        String subjectName;
        String creditType;
        String grade;

        public Session(int sessionNumber, String subjectName, String creditType, String grade) {
            this.sessionNumber = sessionNumber;
            this.subjectName = subjectName;
            this.creditType = creditType;
            this.grade = grade;
        }
    }


    public static List<ZachetBook> readStudents(String filename) throws IOException {
        List<ZachetBook> students = new ArrayList<>();

        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();

                if (line.isEmpty()) continue;

                String[] fio = line.split(" ");
                String lastName = fio[0];
                String firstName = fio[1];
                String patronymic = fio[2];

                line = in.nextLine().trim();
                String[] courseGroup = line.split(" ");
                int course = Integer.parseInt(courseGroup[0]);
                int group = Integer.parseInt(courseGroup[1]);

                line = in.nextLine().trim();
                int sessionNumber = Integer.parseInt(line);

                ZachetBook student = new ZachetBook(lastName, firstName, patronymic, course, group);


                while (in.hasNextLine()) {
                    line = in.nextLine().trim();
                    if (line.equals("---") || line.isEmpty()) break;

                    String[] parts = line.split(" ");
                    if (parts.length < 3) continue;

                    String subjectName = parts[0];
                    String creditType = parts[1];
                    String grade = parts[2];

                    student.sessions.add(student.new Session(sessionNumber, subjectName, creditType, grade));
                }

                students.add(student);
            }
        }

        return students;
    }



    public static boolean isExcellentStudent(ZachetBook student) {
        for (ZachetBook.Session sess : student.sessions) {
            if (sess.creditType.equalsIgnoreCase("зачет")) {
                if (!sess.grade.equalsIgnoreCase("сдан")) {
                    return false;
                }
            } else if (sess.creditType.equalsIgnoreCase("экзамен")) {
                try {
                    int mark = Integer.parseInt(sess.grade);
                    if (mark < 9) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void outputStudents(String filename, List<ZachetBook> books) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (ZachetBook s : books) {
                if (isExcellentStudent(s)) {
                    for (ZachetBook.Session sess : s.sessions) {
                        out.printf(
                                "%s %s %s, %d курс, группа %d, сессия %d, %s, %s%n",
                                s.lastName,
                                s.firstName,
                                s.patronymic,
                                s.course,
                                s.group,
                                sess.sessionNumber,
                                sess.subjectName,
                                sess.grade
                        );
                    }
                }
            }
        }
    }
}


