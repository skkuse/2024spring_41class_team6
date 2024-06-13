package com.example.demo.controller;

import com.example.demo.application.CodeMatchService;
import com.example.demo.domain.CodeMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("code_match")
public class CodeMatchController {

    private final CodeMatchService codeMatchService;

    public CodeMatchController(CodeMatchService codeMatchService) {
        this.codeMatchService = codeMatchService;
    }

    @GetMapping("{before_id}")
    public Optional<CodeMatch> getCodeMatch(@PathVariable("before_id") int before_id) {
        return codeMatchService.findByBeforeId(before_id);
    }
}
