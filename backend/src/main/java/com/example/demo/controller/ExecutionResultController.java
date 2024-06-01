package com.example.demo.controller;

import com.example.demo.application.ExecutionResultService;
import com.example.demo.domain.Code;
import com.example.demo.domain.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExecutionResultController {

    @Autowired
    private ExecutionResultService executionResultService;

    @GetMapping("execution_result/{code_id}")
    public Optional<ExecutionResult> getExecutionResult(@PathVariable("code_id") int code_id) {
        return executionResultService.findByCodeId(code_id);
    }

}
