package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.application.GitApiHandlerService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("git")
@Slf4j
public class GitApiHandlerController {
    
    private final GitApiHandlerService gitApiHandlerService;

    public GitApiHandlerController(GitApiHandlerService gitApiHandlerService) {
        this.gitApiHandlerService = gitApiHandlerService;
    }

    @GetMapping("")
    public String get(@RequestParam String url, @RequestBody String code) {
        this.gitApiHandlerService.run(url, code);
        return "success";
    }
    
}
