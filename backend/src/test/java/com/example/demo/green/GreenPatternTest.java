package com.example.demo.green;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GreenPatternTest {

    GreenPattern greenPattern = new GreenPattern();

    public static String readFileToString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    @Test
    void GreenCodeGenerator() {
        String filePath = System.getProperty("user.dir") + "/src/test/java/com/example/demo/green/BuggyTest.java";
        try {
            String fileContent = readFileToString(filePath);
            System.out.println("######   Buggy Code   ######");
            System.out.println(fileContent);

            System.out.println("######   Fixed Code   ######");
            System.out.println(greenPattern.generateGreenCode(fileContent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
