package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WrapperToPrimitivePattern {
    public ArrayList<String> convertWrappersToPrimitives(ArrayList<String> lines) {
        String[][] mappings = {
                {"Integer", "int"},
                {"Double", "double"},
                {"Float", "float"},
                {"Long", "long"},
                {"Short", "short"},
                {"Byte", "byte"},
                {"Character", "char"},
                {"Boolean", "boolean"}
        };

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            for (String[] mapping : mappings) {
                String wrapper = mapping[0];
                String primitive = mapping[1];

                Pattern pattern = Pattern.compile("\\b" + wrapper + "\\b");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    line = matcher.replaceAll(primitive);
                }

                pattern = Pattern.compile("\\b" + wrapper + "\\.valueOf\\(([^\\)]+)\\)");
                matcher = pattern.matcher(line);

                if (matcher.find()) {
                    line = matcher.replaceAll("$1");
                }

                pattern = Pattern.compile("\\b([a-zA-Z0-9_]+)\\.([a-zA-Z0-9_]+)Value\\(\\)");
                matcher = pattern.matcher(line);

                if (matcher.find() && matcher.group(2).equalsIgnoreCase(primitive)) {
                    line = matcher.replaceAll(matcher.group(1));
                }
            }

            lines.set(i, line);
        }

        return lines;
    }
}
