package com.example.demo.controller;

import com.example.demo.application.ExecuteApplicationService;
import com.example.demo.domain.ExecutionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("execute")
public class ExecuteController {
    private final ExecuteApplicationService executeApplicationService;
    public ExecuteController(
            ExecuteApplicationService executeApplicationService
    ) {
        this.executeApplicationService = executeApplicationService;
    }

    @GetMapping("")
    public ExecutionResult run(@RequestBody String code) {
        return this.executeApplicationService.run(code);
    }
}
