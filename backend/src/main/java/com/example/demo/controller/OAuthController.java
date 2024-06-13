package com.example.demo.controller;

import com.example.demo.application.OAuthApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth")
public class OAuthController {
    private final OAuthApplicationService oAuthApplicationService;

    public OAuthController(
            OAuthApplicationService oAuthApplicationService
    ) {
        this.oAuthApplicationService = oAuthApplicationService;
    }

    @GetMapping("")
    public String auth(@RequestBody String code) {
        return this.oAuthApplicationService.auth(code);
    }
}
