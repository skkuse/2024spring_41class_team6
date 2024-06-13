package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleStringToCharacterPattern {

    private final IndentCalculator indentCalculator = new IndentCalculator();

    public ArrayList<String> convertSingleStringToCharacter(ArrayList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            Pattern patternVar = Pattern.compile("\\bString\\s+([a-zA-Z0-9_]+)\\s*=\\s*\"(.)\";");
            Matcher matcherVar = patternVar.matcher(line);

            if (matcherVar.find()) {
                String variableName = matcherVar.group(1);
                String charValue = matcherVar.group(2);
                String indent = indentCalculator.extractIndentFromLeadingSpaces(line);
                String newLine = indent + "char " + variableName + " = '" + charValue + "';";
                lines.set(i, newLine);
            }
        }

        return lines;
    }
}
