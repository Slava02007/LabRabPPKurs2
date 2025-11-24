package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class PerenosText {
    int size;
    String[] words;

    public PerenosText(String[] words, int size) {
        this.words = words;
        this.size = size;
    }

     public static ArrayList<String> perenos(String text, int size) throws IOException {
        ArrayList<String> result = new ArrayList<>();

        String[] words = text.trim().split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) continue;

            if (currentLine.length() == 0) {
               currentLine.append(word);
            }
            else if (currentLine.length() + word.length() + 1 <= size) {
                currentLine.append(" ").append(word);
            }
            else {
                result.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            result.add(currentLine.toString());
        }

        return result;
    }

    public static ArrayList<String> justifyTextSimple(ArrayList<String> lines, int width) {
        ArrayList<String> justifiedLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (i == lines.size() - 1) {
                justifiedLines.add(line);
                continue;
            }

            String[] words = line.split("\\s+");

            if (words.length <= 1 || line.isEmpty()) {
                justifiedLines.add(line);
                continue;
            }

            int wordsLength = 0;
            for (String word : words) {
                wordsLength += word.length();
            }

            int totalSpacesNeeded = width - wordsLength;
            int gapsCount = words.length - 1;

            if (totalSpacesNeeded <= 0) {
                justifiedLines.add(line);
                continue;
            }

            int baseSpaces = totalSpacesNeeded / gapsCount;
            int extraSpaces = totalSpacesNeeded % gapsCount;

            StringBuilder justifiedLine = new StringBuilder();

            for (int j = 0; j < words.length; j++) {
                justifiedLine.append(words[j]);

                if (j < words.length - 1) {
                    justifiedLine.append(" ".repeat(baseSpaces));
                    if (j < extraSpaces) {
                        justifiedLine.append(" ");
                    }
                }
            }
            justifiedLines.add(justifiedLine.toString());
        }

        return justifiedLines;
    }

    public static ArrayList<String> processText(String text, int width) throws IOException {
        ArrayList<String> lines = perenos(text, width);
        return justifyTextSimple(lines, width);
    }
}