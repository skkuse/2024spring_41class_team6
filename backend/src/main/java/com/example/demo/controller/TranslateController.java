package com.example.demo.controller;

import com.example.demo.application.TranslateApplicationService;
import com.example.demo.application.TranslateApplicationService.TranslateResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("translate")
public class TranslateController {

    private final TranslateApplicationService translateService;
    public TranslateController(TranslateApplicationService translateService) {
        this.translateService = translateService;
    }

    @GetMapping("/{emission}")
    public TranslateResult getTranslateResult(@PathVariable float emission) {
        return this.translateService.translate(emission);
    }
    
}
