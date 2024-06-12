package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixRedundantObjectCreationPattern {

    private final IndentCalculator indentCalculator = new IndentCalculator();

    public ArrayList<String> fixRedundantObjectCreationPattern(ArrayList<String> lines) {
        int objectCreationIndex = 0;
        int loopCreationIndex = 0;
        int lineSize = lines.size();

        for (int i = 0; i < lineSize; i++) {
            String line = lines.get(i);

            Pattern objectPattern = Pattern.compile("\\b[A-Z]\\w*\\s+[a-z]\\w*\\s*=\\s*new\\s+[A-Z]\\w*\\s*\\(\\s*\\)\\s*;");
            Matcher objectMatcher = objectPattern.matcher(line);
            if (objectMatcher.find()) {
                objectCreationIndex = i;
                break;
            }
        }

        for (int i = 0; i < lineSize; i++) {
            String line = lines.get(i);

            Pattern forPattern = Pattern.compile("\\b(?:for|while)\\s*\\(.*?\\)\\s*\\{");
            Matcher forMatcher = forPattern.matcher(line);
            if (forMatcher.find()) {
                loopCreationIndex = i;
                break;
            }
        }

        if (objectCreationIndex != 0) {
            String fixedContentIndent = indentCalculator.extractIndentFromLeadingSpaces(lines.get(objectCreationIndex));
            String loopContentIndent = indentCalculator.extractIndentFromLeadingSpaces(lines.get(loopCreationIndex));

            String fixedContent = lines.get(objectCreationIndex);
            fixedContent = loopContentIndent + fixedContent.substring(fixedContentIndent.length());
            String loopContent = fixedContentIndent + lines.get(loopCreationIndex).substring(loopContentIndent.length());

            lines.set(objectCreationIndex, "##MUSTDELETE##");
            lines.add(loopCreationIndex, fixedContent);

            lines.removeIf(item -> item.equals("##MUSTDELETE##"));
        }

        return lines;
    }
}
