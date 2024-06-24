package com.example.demo.application;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;

import java.io.IOException;
import java.nio.file.Path;

public class GitRepoHandlerService {
    public GitRepoHandlerService() {
        
    }

    private final String GITHUB_OAUTH_TOKEN = null;

    // public void main(Path path, String gitURL, String oauthCode) {
    //     try {
    //         gitCommitAndPush(path);
    //         createPullRequest(path);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    // public static void gitCommitAndPush(String repoPath, String commitMessage, String remoteUrl, String branch, String token) throws IOException, GitAPIException {
    //     try (Git git = Git.open(new File(repoPath))) {
    //         git.add().addFilepattern(".").call();
    //         git.commit().setMessage(commitMessage).call();
    //         git.push()
    //             .setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""))
    //             .setRemote(remoteUrl)
    //             .setRefSpecs(branch)
    //             .call();
    //     }
    // }

    public void createPullRequest(Path path) throws IOException {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(GITHUB_OAUTH_TOKEN).build();
            GHRepository repo = github.getRepository("owner/repo");

            String title = "Pull Request Title";
            String body = "Pull Request Description";
            String head = "your_username:testbranch";
            String base = "master";

            repo.createPullRequest(title, head, base, body);
            System.out.println("Pull Request created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void gitCommitAndPushByCLI(Path path) throws IOException {
    //     try {
    //         execCommand(path, false, "git", "-C", path.toString(), "init");
    //         execCommand(path, false, "git", "-C", path.toString(), "remote", "add", "origin", "https://github.com/Lee-won-hyeok/testrepo.git");
    //         execCommand(path, false, "git", "-C", path.toString(), "branch", "-M", "testbranch");
    //         execCommand(path, false, "git", "-C", path.toString(), "add", ".");
    //         execCommand(path, false, "git", "-C", path.toString(), "commit", "-m", "testcommit");
    //         execCommand(path, true, "git", "-C", path.toString(), "push", "origin", "testbranch");
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void execCommand(Path path, boolean log, String... commands) throws IOException, InterruptedException {
    //     ProcessBuilder processBuilder = new ProcessBuilder();
    //     processBuilder.command(commands);

    //     if (log) processBuilder.redirectErrorStream(true);

    //     Process process = processBuilder.start();
    //     if (log) {
    //         InputStream inputStream = process.getInputStream();
    //         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             System.out.println(line);
    //         }
    //     }
    //     int exitCode = process.waitFor();

    //     if (exitCode == 0) {
    //         System.out.println("Command executed successfully: " + String.join(" ", commands));
    //     } else {
    //         System.out.println("Command failed with exit code " + exitCode + ": " + String.join(" ", commands));
    //     }
    // }
}
