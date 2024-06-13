package com.example.demo.green;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class GreenPattern {

    private final FixArrayListSizePattern fixArrayListSizePattern = new FixArrayListSizePattern();
    private final FixNestedIfPattern fixNestedIfPattern = new FixNestedIfPattern();
    private final FixRedundantObjectCreationPattern fixRedundantObjectCreationPattern = new FixRedundantObjectCreationPattern();
    private final WrapperToPrimitivePattern wrapperToPrimitivePattern = new WrapperToPrimitivePattern();
    private final SingleStringToCharacterPattern singleStringToCharacterPattern = new SingleStringToCharacterPattern();
    private final StringToStringBuilderPattern stringToStringBuilder = new StringToStringBuilderPattern();

    public String generateGreenCode(String buggyCode) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            String[] codes = buggyCode.split("\n");
            lines = new ArrayList<>(Arrays.asList(codes));

            lines = wrapperToPrimitivePattern.convertWrappersToPrimitives(lines);
            lines = fixArrayListSizePattern.fixArrayListSizePattern(lines);
            lines = fixNestedIfPattern.fixNestedIfPattern(lines);
            lines = fixRedundantObjectCreationPattern.fixRedundantObjectCreationPattern(lines);
            lines = stringToStringBuilder.convertStringToStringBuilder(lines);
            lines = singleStringToCharacterPattern.convertSingleStringToCharacter(lines);

        } catch (Exception e) {
            System.out.println("GreenPattern.generateGreenCode: Error\n");
        }

        return String.join("\n", lines);
    }
}