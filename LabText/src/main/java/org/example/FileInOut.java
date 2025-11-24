package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInOut {
    String fileinput;
    String fileoutput;

    public FileInOut(String fileinput, String fileoutput) {
        this.fileinput = fileinput;
        this.fileoutput = fileoutput;
    }

    public static ArrayList<String> readFile(String fileinput) throws IOException {
        ArrayList<String> text = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fileinput))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    text.add(line);
                }
            }
            return text;
        }
    }

    public static void writeFile(String fileoutput, ArrayList<String> text) throws IOException {
        try (PrintWriter pr = new PrintWriter(new FileWriter(fileoutput))) {
            for (String line : text) {
                pr.println(line);
            }
        }
    }
}