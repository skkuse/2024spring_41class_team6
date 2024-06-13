package com.example.demo.controller;

import com.example.demo.application.RepoService;
import com.example.demo.domain.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("repo")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @PostMapping("")
    public Repo createCode(@RequestBody Repo repo) {
        return repoService.createRepo(repo);
    }

}
