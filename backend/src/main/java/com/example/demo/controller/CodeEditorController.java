package com.example.demo.controller;

import com.example.demo.application.CodeEditorRequest;
import com.example.demo.application.CodeEditorService;
import com.example.demo.domain.Code;
import com.example.demo.domain.CodeEditorResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("code_editor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CodeEditorController {

    private final CodeEditorService codeEditorService;

    public CodeEditorController(CodeEditorService codeEditorService) {
        this.codeEditorService = codeEditorService;
    }

    @PostMapping("")
    public CodeEditorResult getResult(@RequestBody CodeEditorRequest request) {
        return codeEditorService.process(request.getCode());
    }

}
