package com.example.demo.green;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixNestedIfPattern {

    public ArrayList<String> fixNestedIfPattern(ArrayList<String> lines) {

        boolean tripleNestedIfFound = false;
        boolean doubleNestedIfFound = false;
        int firstStartIfIndex = 0, firstEndIfIndex = 0, secondStartIfIndex = 0, secondEndIfIndex = 0,
                thirdStartIfIndex = 0, thirdEndIfIndex = 0;
        String firstCondition = "", secondCondition = "", thirdCondition = "";
        int lineSize = lines.size();

        Pattern pattern = Pattern.compile("\\((.*?)\\)");

        for (int i = 0; i < lineSize; i++) {
            String line = lines.get(i);

            if (line.contains("if(") || line.contains("if (")) {
                Matcher matcher1 = pattern.matcher(line);
                firstStartIfIndex = i;

                if (matcher1.find()) {
                    firstCondition = matcher1.group(1);
                }

                for (int j = i + 1; j < lineSize; j++) {
                    String line2 = lines.get(j);

                    if(!doubleNestedIfFound && line2.contains("}")){
                        doubleNestedIfFound = false;
                        break;
                    }

                    if (line2.contains("if(") || line2.contains("if (")) {
                        doubleNestedIfFound = true;
                        secondStartIfIndex = j;
                        Matcher matcher2 = pattern.matcher(line2);

                        if (matcher2.find()) {
                            secondCondition = matcher2.group(1);
                        }

                        for (int k = j + 1; k < lineSize; k++) {
                            String line3 = lines.get(k);

                            if(!tripleNestedIfFound && line3.contains("}")) {
                                tripleNestedIfFound = false;
                                break;
                            }

                            if (line3.contains("if(") || line3.contains("if (")) {
                                tripleNestedIfFound = true;
                                thirdStartIfIndex = k;

                                Matcher matcher3 = pattern.matcher(line3);

                                if (matcher3.find()) {
                                    thirdCondition = matcher3.group(1);
                                }

                                for (int l = k + 1; l < lineSize; l++) {
                                    String line4 = lines.get(l);
                                    if (line4.contains("}")) {
                                        thirdEndIfIndex = l;
                                        break;
                                    }
                                }

                                int count = 0;

                                for (int l = k + 1; l < lineSize; l++) {
                                    String line4 = lines.get(l);

                                    if (line4.contains("}")) {
                                        count++;
                                        if (count == 3) {
                                            firstEndIfIndex = l;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (tripleNestedIfFound) break;
                        }

                        if (!tripleNestedIfFound) {
                            for (int k = j + 1; k < lineSize; k++) {
                                String line3 = lines.get(k);
                                if (line3.contains("}")) {
                                    secondEndIfIndex = k;
                                    break;
                                }
                            }

                            for (int k = secondStartIfIndex + 1; k < lineSize; k++) {
                                String line3 = lines.get(k);
                                if (line3.contains("}")) {
                                    firstEndIfIndex = k;
                                    break;
                                }
                            }
                        }
                    }
                    if (tripleNestedIfFound || doubleNestedIfFound) {
                        break;
                    }
                }
                if (tripleNestedIfFound || doubleNestedIfFound) {
                    break;
                }
            }
        }

        if (tripleNestedIfFound) {
            String conditionBody = "";
            int ifIndex = lines.get(firstStartIfIndex).indexOf("if");
            String baseIndent = (ifIndex != -1) ? lines.get(firstStartIfIndex).substring(0, ifIndex) : "";

            for (int i = thirdStartIfIndex + 1; i < thirdEndIfIndex; i++) {
                conditionBody = conditionBody + (lines.get(i).trim() + "\n");
            }

            for (int i = firstStartIfIndex; i <= firstEndIfIndex; i++) {
                lines.set(i, "##MUSTDELETE##");
            }

            lines.set(firstStartIfIndex, baseIndent + "if((" + firstCondition + " && " + secondCondition + ") && " + thirdCondition + ") {\n");
            lines.add(firstStartIfIndex + 1, baseIndent + "\t" + conditionBody);
            lines.add(firstStartIfIndex + 2, baseIndent + "}");

            lines.removeIf(item -> item.equals("##MUSTDELETE##"));
        }
        else if (doubleNestedIfFound) {
            String conditionBody = "";
            int ifIndex = lines.get(firstStartIfIndex).indexOf("if");
            String baseIndent = (ifIndex != -1) ? lines.get(firstStartIfIndex).substring(0, ifIndex) : "";

            for (int i = secondStartIfIndex + 1; i < secondEndIfIndex; i++) {
                conditionBody = conditionBody + (lines.get(i).trim() + "\n");
            }

            for (int i = firstStartIfIndex; i <= firstEndIfIndex; i++) {
                lines.set(i, "##MUSTDELETE##");
            }

            lines.set(firstStartIfIndex, baseIndent + "if(" + firstCondition + " && " + secondCondition + ") {\n");
            lines.add(firstStartIfIndex + 1, baseIndent + "\t" + conditionBody);

            lines.removeIf(item -> item.equals("##MUSTDELETE##"));
        }
        else{
            return lines;
        }

        return fixNestedIfPattern(lines);
    }
}
