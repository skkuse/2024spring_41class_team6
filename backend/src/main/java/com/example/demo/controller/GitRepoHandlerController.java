package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.application.GitApiHandlerService;

@RestController
@RequestMapping("gitrepo")
public class GitRepoHandlerController {
    
    private final GitApiHandlerService gitApiHandlerService;

    public GitRepoHandlerController(GitApiHandlerService gitApiHandlerService) {
        this.gitApiHandlerService = gitApiHandlerService;
    }
    
}
