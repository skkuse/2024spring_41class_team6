package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToStringBuilderPattern {

    private final IndentCalculator indentCalculator = new IndentCalculator();

    public ArrayList<String> convertStringToStringBuilder(ArrayList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            Pattern pattern = Pattern.compile("\\bString\\s+([a-zA-Z0-9_]+)\\s*=\\s*\"(.*)\";");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String variableName = matcher.group(1);
                String stringValue = matcher.group(2);
                String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                String newLine = indent + "StringBuilder " + variableName + " = new StringBuilder(\"" + stringValue + "\");";
                lines.set(i, newLine);
            }

            pattern = Pattern.compile("\\b([a-zA-Z0-9_]+)\\s*\\+=\\s*\"(.*)\";");
            matcher = pattern.matcher(line);

            if (matcher.find()) {
                String variableName = matcher.group(1);
                String appendValue = matcher.group(2);
                String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                String newLine = indent + variableName + ".append(\"" + appendValue + "\");";
                lines.set(i, newLine);
            }
        }

        return lines;
    }
}
