package com.example.demo.application;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Service
public class GitRepoHandlerService {
    public GitRepoHandlerService() { }

    // public void gitCommitAndPush(Path repoPath, String commitMessage, String remoteUrl, String branch, String oauthToken) throws IOException {
    //     try {
    //         execCommand(repoPath, true, "git", "init");
    //         execCommand(repoPath, true, "git", "remote", "add", "origin", remoteUrl);

    //         execCommand(repoPath, true, "git", "config", "credential.username", "oauth_token");
    //         execCommand(repoPath, true, "git", "config", "credential.helper", "'store --file=.git/credentials'");

    //         execCommand(repoPath, true, "git", "checkout", "-b", branch);
    //         execCommand(repoPath, true, "git", "add", ".");
    //         execCommand(repoPath, true, "git", "commit", "-m", commitMessage);
    //         execCommand(repoPath, true, "git", "push", "-u", "origin", branch, "--force");
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void gitCommitAndPushByCLI(Path path, String repoURL, String branch, String oauthToken) throws IOException {
        try {
            execCommand(path, false, "git", "-C", path.toString(), "init");
            execCommand(path, false, "git", "-C", path.toString(), "remote", "add", "origin", repoURL);
            execCommand(path, false, "git", "-C", path.toString(), "branch", "-M", branch);
            execCommand(path, false, "git", "-C", path.toString(), "add", ".");
            execCommand(path, false, "git", "-C", path.toString(), "commit", "-m", "Greenize Pattern Matched");
            execCommand(path, true, "git", "-C", path.toString(), "push", "origin", branch);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void execCommand(Path path, boolean log, String... commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);

        if (log) processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        if (log) {
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Command executed successfully: " + String.join(" ", commands));
        } else {
            System.out.println("Command failed with exit code " + exitCode + ": " + String.join(" ", commands));
        }
    }
}
