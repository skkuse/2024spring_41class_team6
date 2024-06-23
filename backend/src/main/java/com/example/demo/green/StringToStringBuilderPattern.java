package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToStringBuilderPattern {

    private final IndentCalculator indentCalculator = new IndentCalculator();

    public ArrayList<String> convertStringToStringBuilder(ArrayList<String> lines) {
        Boolean isStringMade = false;
        Boolean isStringChanged = false;
        String RealVariableName = "";

        // 첫 번째 루프: String 변수를 StringBuilder로 변환하고 append()로 변경
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            Pattern pattern = Pattern.compile("\\bString\\s+([a-zA-Z0-9_]+)\\s*=\\s*\"(.*)\";");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String variableName = matcher.group(1);
                RealVariableName = matcher.group(1);
                String stringValue = matcher.group(2);
                String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                String newLine = indent + "StringBuilder " + "sb_" + variableName + " = new StringBuilder(\"" + stringValue + "\");";
                lines.set(i, newLine);
                isStringChanged = true;
            }

            pattern = Pattern.compile("\\b([a-zA-Z0-9_]+)\\s*\\+=\\s*\"(.*)\";");
            matcher = pattern.matcher(line);

            if (matcher.find()) {
                String variableName = matcher.group(1);
                String appendValue = matcher.group(2);
                String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                String newLine = indent + "sb_" + variableName + ".append(\"" + appendValue + "\");";
                lines.set(i, newLine);
                isStringChanged = true;
            }
        }

        // 두 번째 루프: RealVariableName을 사용하기 전에 String으로 변환
        if (isStringChanged) {
            ArrayList<String> updatedLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(RealVariableName) && !line.contains("StringBuilder") && !line.contains("append")) {
                    String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                    if (!isStringMade) {
                        // 처음 사용되는 경우 String 변환 추가
                        updatedLines.add("\n" + indent + "String " + RealVariableName + " = sb_" + RealVariableName + ".toString();");
                        isStringMade = true;
                    } else {
                        // 이후 사용되는 경우 String 변환 갱신
                        updatedLines.add("\n" + indent + RealVariableName + " = sb_" + RealVariableName + ".toString();");
                    }
                }
                updatedLines.add(line);
            }
            lines = updatedLines;
        }

        return lines;
    }
}
