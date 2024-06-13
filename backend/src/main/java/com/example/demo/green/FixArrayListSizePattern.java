package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixArrayListSizePattern {

    private final IndentCalculator indentCalculator = new IndentCalculator();

    public ArrayList<String> fixArrayListSizePattern(ArrayList<String> lines) {
        boolean isDetected = false;
        int lineSize = lines.size();
        String arrayListVariableName = "";
        int buggyLine = -1;

        for (int i = 0; i < lineSize; i++) {
            String line = lines.get(i);

            Pattern pattern = Pattern.compile("\\bArrayList\\s*<[^>]*>\\s+([a-zA-Z0-9_]+)\\s*=");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                arrayListVariableName = matcher.group(1);

                for (int j = i; j < lineSize; j++) {
                    String line2 = lines.get(j);
                    Pattern sizeMethodPattern = Pattern.compile("\\b" + arrayListVariableName + "\\.size\\(\\)");

                    Matcher sizeMethodMatcher = sizeMethodPattern.matcher(line2);
                    if (sizeMethodMatcher.find()) {
                        buggyLine = j;
                        isDetected = true;
                        break;
                    }
                }
                break;
            }
        }

        if (isDetected) {
            int count = indentCalculator.countLeadingSpaces(lines.get(buggyLine));
            lines.set(buggyLine, lines.get(buggyLine).replace(arrayListVariableName + ".size()", arrayListVariableName + "Size"));

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                builder.append(' ');
            }
            builder.append("int ").append(arrayListVariableName).append("Size = ").append(arrayListVariableName).append(".size();\n");
            lines.add(buggyLine, builder.toString());
        }

        return lines;
    }
}
