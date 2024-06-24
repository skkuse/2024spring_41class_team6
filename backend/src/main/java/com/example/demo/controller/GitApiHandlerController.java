package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.application.GitApiHandlerService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("git")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class GitApiHandlerController {
    
    private final GitApiHandlerService gitApiHandlerService;

    public GitApiHandlerController(GitApiHandlerService gitApiHandlerService) {
        this.gitApiHandlerService = gitApiHandlerService;
    }

    private class ResponseObject {
        String url;

        public ResponseObject(String url) {
            this.url = url;
        }
    }

    @GetMapping("")
    public ResponseObject get(@RequestBody String gitURL, @RequestBody String code) {
        String ret = this.gitApiHandlerService.run(gitURL, code);
        return new ResponseObject(ret);   
    }
    
}
