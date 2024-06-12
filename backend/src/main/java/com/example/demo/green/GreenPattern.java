package com.example.demo.green;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GreenPattern {

    private final FixArrayListSizePattern fixArrayListSizePattern = new FixArrayListSizePattern();
    private final FixNestedIfPattern fixNestedIfPattern = new FixNestedIfPattern();
    private final FixRedundantObjectCreationPattern fixRedundantObjectCreationPattern = new FixRedundantObjectCreationPattern();
    private final WrapperToPrimitivePattern wrapperToPrimitivePattern = new WrapperToPrimitivePattern();

    public String generateGreenCode(String buggyCode) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            String[] codes = buggyCode.split("\n");
            lines = new ArrayList<>(Arrays.asList(codes));

            lines = wrapperToPrimitivePattern.convertWrappersToPrimitives(lines);
            lines = fixArrayListSizePattern.fixArrayListSizePattern(lines);
            lines = fixNestedIfPattern.fixNestedIfPattern(lines);
            lines = fixRedundantObjectCreationPattern.fixRedundantObjectCreationPattern(lines);


        } catch (Exception e) {
            System.out.println("GreenPattern.generateGreenCode: Error\n");
        }

        return String.join("\n", lines);
    }
}
