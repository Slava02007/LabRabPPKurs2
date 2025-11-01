package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String file1="input.txt";
        String file2="output.txt";

        List<ZachetBook> students = ZachetBook.readStudents(file1);
        ZachetBook.outputStudents(file2, students);
    }
}

