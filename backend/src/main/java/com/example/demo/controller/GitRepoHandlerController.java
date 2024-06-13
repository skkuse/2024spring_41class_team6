package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.application.GitApiHandlerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("gitrepo")
public class GitRepoHandlerController {
    
    private final GitApiHandlerService gitApiHandlerService;

    public GitRepoHandlerController(GitApiHandlerService gitApiHandlerService) {
        this.gitApiHandlerService = gitApiHandlerService;
    }

    // @GetMapping("")
    // public String getMethodName(@RequestParam String param) {
    //     return new String();
    // }
    
}
