package com.example.demo.application;

import org.apache.tomcat.util.digester.DocumentProperties.Charset;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.StringTokenizer;
import java.util.Random;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.zip.*;

import com.example.demo.domain.Code;
import com.google.gson.*;
import com.example.demo.domain.CodeMatch;
import com.example.demo.green.GreenPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Slf4j
public class GitApiHandlerService {
    @Autowired
    private OAuthApplicationService oAuthApplicationService;
    @Autowired
    private ExecuteApplicationService executeApplicationService;
    @Autowired
    private RepoService repoService;
    @Autowired
    private CodeEditorService codeEditorService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private GreenPattern greenPattern;
    @Autowired
    private CodeMatchService codeMatchService;
    @Autowired
    private GitRepoHandlerService gitRepoHandlerService;

    private String Base_URL = "https://api.github.com/";
    private String access_token;
    WebClient webClient;
    Gson gson;

    public GitApiHandlerService() {
        webClient = WebClient.builder()
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            // .defaultHeader("Accept", "application/vnd.github+json")
            .build();
        gson = new Gson();
    }

    @PostConstruct
    public void init() {
        access_token = this.oAuthApplicationService.auth("code_here");
    }

    // GET Request -> return Body
    private String getReq(String url) {
        String res;
        try {
            res = webClient.method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

        return res;
    }
    
    // GET Request -> handle Redirect for get Download Link
    public String getLocationHeader(String url) {
        Mono<String> ret;
        try{
            ret = webClient.get()
                .uri(url)
                .exchangeToMono(response -> {
                    return Mono.justOrEmpty(response.headers().header("Location").stream().findFirst());   
                });
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return null;
        }
        return ret.block();
    }

    // GET Request -> return File (zip)
    private int getFileReq(String url, Path filePath) {
        try {
            Flux<DataBuffer> buf = webClient.method(HttpMethod.GET)
                    .uri(url)
                    // .header("Authorization", String.format("Bearer %s", this.access_token))
                    .retrieve()
                    .bodyToFlux(DataBuffer.class)
                    .doOnComplete(() -> log.info("File downloaded successfully"));

            DataBufferUtils.write(buf, filePath, StandardOpenOption.CREATE)
                .share()
                .block();

            log.info("File: {} {}",  filePath, Files.size(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    // Unzip File in Temporary dir.
    private static void unzip(Path zipPath, Path targetPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }
                Path newPath = zipSlipProtect(zipEntry, targetPath);
                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Check Illegal path while unzip
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetPath) throws IOException {
        Path targetDirResolved = targetPath.resolve(zipEntry.getName());
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetPath)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }

    // Object for Github URL
    @Data
    private class ParsedURL {
        String repo;
        String owner;
        String ref;
        String branch;
    }

    // Object to Save Git API Response
    @Data
    private class GetRepoResponseObject {
        int id;
        class res {
            String updated_at;
        }
    }

    // Input URL parsing & validation -> ParsedURL Object
    private ParsedURL parseURL(String url) {
        StringTokenizer tokenizer = new StringTokenizer(url, "/");
        String segment;
        ParsedURL parsedURL = new ParsedURL();
        while (tokenizer.hasMoreTokens()) {
            segment = tokenizer.nextToken();
            if ("github.com".equals(segment)) {
                parsedURL.setOwner(tokenizer.nextToken());
                parsedURL.setRepo(tokenizer.nextToken());
            }
            if ("branch".equals(segment)) {
                parsedURL.setBranch(tokenizer.nextToken());
                break;
            }
        }
        if (parsedURL.getOwner() == null || parsedURL.getRepo() == null) {
            throw new Error("Invalid URL");
        }

        return parsedURL;
    }

    // Same as Git Clone (Download zip file & Unzip in temp DIR) -> return temp DIR.
    private Path clone(String url) {
        Path filePath = null;
        Path tempDir;
        Path zipPath;
        
        try {
            tempDir = Files.createTempDirectory("java-repo");
            zipPath = tempDir.resolve("Temp.zip");

            if (this.getFileReq(url, zipPath) == 0) {
                log.error("FileReq failed");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // unzip
        try {
            Path targetPath = tempDir.resolve("unzip");
            GitApiHandlerService.unzip(zipPath, targetPath);
            filePath = targetPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // detect main & return Path

        return filePath;
    }

    // Get all .java files from directory
    public ArrayList<Path> getJavaFiles(Path path) {
        ArrayList<Path> ret = new ArrayList<Path>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".java")) {
                        ret.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // Read file -> return the content (String)
    public String readFileContent(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void overwriteFile(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static void execCommand(Path path, Boolean log, String... commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);

        if(log) processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        if(log) {
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

    public static void gitCommitAndPush(Path path) throws IOException {
        try {
            execCommand(path, false, "git", "-C", path.toString(), "init");
            execCommand(path, false, "git", "-C", path.toString(), "remote", "add", "origin", "https://github.com/Lee-won-hyeok/testrepo.git");
            execCommand(path, false, "git", "-C", path.toString(), "branch", "-M", "testbranch");
            execCommand(path, false, "git", "-C", path.toString(), "add", ".");
            execCommand(path, false, "git", "-C", path.toString(), "commit", "-m", "testcommit");
            execCommand(path, true, "git", "-C", path.toString(), "push", "origin", "testbranch");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Service runtime
    public void run(String url, String oauthCode) {
        log.info("Requested URL: " + url);
        ParsedURL purl = this.parseURL(url);

        String repoReqURL = Base_URL + String.format(
            "repos/%s/%s",
            purl.getOwner(),
            purl.getRepo()
        );

        String rawJsonString = this.getReq(repoReqURL);
        GetRepoResponseObject res = this.gson.fromJson(rawJsonString, GetRepoResponseObject.class);

        log.info("URL ID: " + res.getId());

        // TODO: GetRepoById
        // TODO: If DB have a record after last_update then return the record

        // .zip download:   /repos/{owner}/{repo}/zipball/{ref}
        String downloadReqURL = Base_URL + String.format(
            "repos/%s/%s/zipball",
            purl.getOwner(),
            purl.getRepo()
        );

        String downloadURL = this.getLocationHeader(downloadReqURL);

        log.info("Download URL: " + downloadURL);
        Path repoPath = clone(downloadURL);

        log.info("Saved & Unzipped in: " + repoPath);

        // TODO: Executable? -> RUN
        // TODO: GET RUNTIME RESULT & CALCULATE EMISSION

        // Greenize
        ArrayList<Path> files = getJavaFiles(repoPath);

        // Read .java file & Pattern Matching
        for (Path file: files) {
            String code = readFileContent(file);
            Code newCode = new Code(code);
            newCode = codeService.createCode(newCode);
            String greenCode = greenPattern.generateGreenCode(newCode.getCode());
            // log.info("matched Code: "+ greenCode);
            Code newGreenCode = new Code(greenCode);
            newGreenCode = codeService.createCode(newGreenCode);
            codeMatchService.createCodeMatch(new CodeMatch(newCode.getId(), newGreenCode.getId()));

            // rewrite in original file path
            try {
                overwriteFile(file, greenCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // PR API
        try{ 
            gitCommitAndPush(repoPath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // gitRepoHandlerService.main(repoPath, url, oauthCode);

        // return ;
    }
}
