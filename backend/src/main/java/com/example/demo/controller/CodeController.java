package com.example.demo.controller;

import com.example.demo.application.CodeService;
import com.example.demo.domain.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping("code")
    public Code createCode(@RequestBody Code code) {
        return codeService.createCode(code);
    }

    @GetMapping("/code/{code_id}")
    public Optional<Code> getCode(@PathVariable int code_id) {
        return codeService.findById(code_id);
    }

    @GetMapping("/repo_code/{repo_id}")
    public List<Code> getCodesByRepoId(@PathVariable Integer repo_id) {
        return codeService.getAllCodesByRepoId(repo_id);
    }
}
