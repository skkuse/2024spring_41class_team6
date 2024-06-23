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

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.zip.*;

import com.google.gson.*;

@Service
@Slf4j
public class GitApiHandlerService {
    @Autowired
    private OAuthApplicationService oAuthApplicationService;
    @Autowired
    private ExecuteApplicationService executeApplicationService;
    @Autowired
    private RepoService repoService;

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
    
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetPath) throws IOException {
        Path targetDirResolved = targetPath.resolve(zipEntry.getName());
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetPath)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }

    @Data
    private class ParsedURL {
        String repo;
        String owner;
        String ref;
        String branch;
    }

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

    private Path getFile(String url) {
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

        // try unzip
        try {
            Path targetPath = tempDir.resolve("unzip");
            // Path targetPath = Paths.get("src", "main", "resources", String.format("%f",randomVal));
            GitApiHandlerService.unzip(zipPath, targetPath);
            filePath = targetPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // detect main & return Path

        return filePath;
    }

    @Data
    private class GetRepoResponseObject {
        int id;
        class res {
            String updated_at;
        }
    }

    public void run(String url) {
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
        Path repoPath = getFile(downloadURL);

        log.info("Saved & Unzipped in: " + repoPath);

        

        // if executable: try run
        // ExecutionResult executionResult = executeApplicationService.run(code);

        // get runtime

        // calculate emission

        // try greenize

        // PR API
        
        
        // return executionResult;
    }
}
