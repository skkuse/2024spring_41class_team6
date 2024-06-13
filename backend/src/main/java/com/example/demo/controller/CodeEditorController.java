package com.example.demo.controller;

import com.example.demo.application.CodeEditorService;
import com.example.demo.domain.Code;
import com.example.demo.domain.CodeEditorResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("code_editor")
public class CodeEditorController {

    private final CodeEditorService codeEditorService;

    public CodeEditorController(CodeEditorService codeEditorService) {
        this.codeEditorService = codeEditorService;
    }

    @GetMapping("")
    public CodeEditorResult getResult(@RequestBody String code) {
        return codeEditorService.process(code);
    }

}
