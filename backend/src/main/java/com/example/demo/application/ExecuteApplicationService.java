package com.example.demo.application;

import com.example.demo.domain.Code;
import com.example.demo.domain.ExecutionResult;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExecuteApplicationService {

    private float PUE;
    private int PSF;
    private int n_CPUcores;
    private float CPUpower;
    private int n_CPU;
    private float MEMpower;
    private float carbonIntensity;

    public ExecuteApplicationService() {
        PUE = 1.2f;
        PSF = 1;

        n_CPUcores = 16;
        CPUpower = 6.6f;
        n_CPU = 1;

        // W/GB
        MEMpower = 0.3725f;
        carbonIntensity = 415.6f;
    }

    private static String detectClassName(String content) {
        Pattern pattern = Pattern.compile("\\bclass\\s+(\\w+)");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    public ExecutionResult run(Code code) {
        double cpu = 0;
        long memory = 0, runtime = 0;
        StringBuilder output = new StringBuilder();
        try {
            Path tempDir = Files.createTempDirectory("java-code");

            String content = new String(code.getCode().getBytes());

            Files.write(
                    tempDir.resolve("Temp.java"),
                    content.replaceAll("\\bclass\\s+" + detectClassName(content) + "\\b", "class Temp").getBytes());

            ClassPathResource scriptResource = new ClassPathResource("run.sh");
            Path scriptFile = tempDir.resolve("run.sh");
            Files.copy(scriptResource.getInputStream(), scriptFile, StandardCopyOption.REPLACE_EXISTING);

            scriptFile.toFile().setExecutable(true);

            ProcessBuilder processBuilder = new ProcessBuilder(scriptFile.toString()).directory(tempDir.toFile());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            int i = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    switch (i) {
                        case 0:
                            cpu = Double.parseDouble(line);
                            i++;
                            break;
                        case 1:
                            memory = Long.parseLong(line);
                            i++;
                            break;
                        case 2:
                            runtime = Long.parseLong(line);
                            i++;
                            break;
                        default:
                            output.append(line).append("\n");
                    }
                }
            }

            process.waitFor(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ExecutionResult(
                    code.getId(),
                    "TIME_EXCEEDED",
                    0L,
                    0L,
                    0,
                    ""
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExecutionResult(
                code.getId(),
                "SUCCESS",
                runtime,
                memory,
                caculate(runtime, memory),
                output.toString()
        );
    }

    public float caculate(long runtime, long memory) {
        // runime(H) memory(GB)
        float runtimeH = (float) runtime/3600000/1000000;
        float memGB = (float) memory/1073741824L;
        float CPU_consumption = this.n_CPUcores * this.CPUpower * this.n_CPU;
        float Mem_consumption = this.MEMpower * memGB;
        float totalConsumption = this.PUE * (CPU_consumption + Mem_consumption) * runtimeH * this.PSF / 1000;
        float emission = totalConsumption * this.carbonIntensity;

        return emission;
    }
}