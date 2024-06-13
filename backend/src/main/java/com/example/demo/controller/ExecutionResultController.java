package com.example.demo.controller;

import com.example.demo.application.ExecutionResultService;
import com.example.demo.domain.Code;
import com.example.demo.domain.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("execution_result")
public class ExecutionResultController {

    private final ExecutionResultService executionResultService;

    public ExecutionResultController(ExecutionResultService executionResultService) {
        this.executionResultService = executionResultService;
    }

    @GetMapping("{code_id}")
    public Optional<ExecutionResult> getExecutionResult(@PathVariable("code_id") int code_id) {
        return executionResultService.findByCodeId(code_id);
    }

}
