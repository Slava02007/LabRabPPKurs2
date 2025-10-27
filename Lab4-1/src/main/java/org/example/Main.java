package org.example;


import java.util.*;
import java.io.*;



class Student{
    long num;
    String name;
    int group;
    double grade;

    public Student(long num, String name, int group, double grade) {
        this.num = num;
        this.name = name;
        this.group = group;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return num + " " + name + " " + group + " " + grade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return num == s.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }


}


public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try {
           System.out.print("Введите имя первого файла: ");
            String file1 = sc.nextLine().trim();

            System.out.print("Введите имя второго файла: ");
            String file2 = sc.nextLine().trim();

            System.out.print("Введите имя выходного файла: ");
            String outfile = sc.nextLine().trim();

            List<Student> list1 = readStudents(file1);
            List<Student> list2 = readStudents(file2);

            System.out.println("Выберите операцию:");
            System.out.println("1 - Объединение (∪)");
            System.out.println("2 - Пересечение (∩)");
            System.out.println("3 - Разность (−)");
            System.out.print("Ваш выбор: ");
            int choice = sc.nextInt();

            Set<Student> result = new HashSet<>();

            switch (choice) {
                case 1 -> {
                    result.addAll(list1);
                    result.addAll(list2);
                    System.out.println("Операция: объединение");
                }
                case 2 -> {
                    for (Student s : list1) {
                        if (list2.contains(s)) result.add(s);
                    }
                    System.out.println("Операция: пересечение");
                }
                case 3 -> {
                    for (Student s : list1) {
                        if (!list2.contains(s)) result.add(s);
                    }
                    System.out.println("Операция: разность");
                }
                default -> {
                    System.out.println("Неверный выбор");
                    return;
                }
            }

            writeStudents(outfile, result);
            System.out.println("Результат записан в файл: " + outfile);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static List<Student> readStudents(String filename) throws IOException {
        List<Student> list = new ArrayList<>();
        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNext()) {
                long num = in.nextLong();
                String name = in.next();
                int group = in.nextInt();
                double grade = in.nextDouble();
                list.add(new Student(num, name, group, grade));
            }
        }
        return list;
    }

    static void writeStudents(String filename, Collection<Student> students) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (Student s : students) {
                out.println(s);
            }
        }
    }

}


