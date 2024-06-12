package com.example.demo.application;

import com.example.demo.domain.Code;
import com.example.demo.domain.ExecutionResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

@Service
public class ExecuteApplicationService {
    public ExecutionResult run(Code code) {
        long cpu = 0, memory = 0, runtime = 0;
        StringBuilder output = new StringBuilder();
        try {
            Path tempDir = Files.createTempDirectory("java-code");

            Path javaFile = Files.write(tempDir.resolve("Temp.java"), code.getCode().getBytes());

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
                            cpu = Long.parseLong(line);
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
                0, // TODO: calculate emmision,
                output.toString()
        );
    }
}
