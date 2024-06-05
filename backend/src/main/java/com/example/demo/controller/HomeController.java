package com.example.demo.controller;

import com.example.demo.application.HomeApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {
    private final HomeApplicationService homeApplicationService;

    HomeController(
            HomeApplicationService homeApplicationService
    ) {
        this.homeApplicationService = homeApplicationService;
    }

    @GetMapping("")
    public String getDefault() {
        return "OK";
    }

    @GetMapping("test")
    public Long getTest() {
        return this.homeApplicationService.getTest();
    }
}
