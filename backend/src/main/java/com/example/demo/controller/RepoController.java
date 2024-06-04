package com.example.demo.controller;

import com.example.demo.application.RepoService;
import com.example.demo.domain.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @PostMapping("repo")
    public Repo createCode(@RequestBody Repo repo) {
        return repoService.createRepo(repo);
    }

}
