package com.example.demo.green;

public class IndentCalculator {

    public String extractIndentFromLeadingSpaces(String line) {
        int count = countLeadingSpaces(line);
        return line.substring(0, count);
    }

    public int countLeadingSpaces(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
