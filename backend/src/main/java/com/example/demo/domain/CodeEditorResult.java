package com.example.demo.domain;

import com.example.demo.application.TranslateApplicationService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeEditorResult {

    // 개선된 코드
    private String greenCode;

    // 실행 결과
    private ExecutionResult beforeExecutionResult;
    private ExecutionResult afterExecutionResult;

    // 비교지표
    private TranslateApplicationService.TranslateResult beforeTranslateResult;
    private TranslateApplicationService.TranslateResult afterTranslateResult;


    public CodeEditorResult(String greenCode, ExecutionResult beforeExecutionResult, ExecutionResult afterExecutionResult,
                            TranslateApplicationService.TranslateResult beforeTranslateResult,
                            TranslateApplicationService.TranslateResult afterTranslateResult) {
        this.greenCode = greenCode;
        this.beforeExecutionResult = beforeExecutionResult;
        this.afterExecutionResult = afterExecutionResult;
        this.beforeTranslateResult = beforeTranslateResult;
        this.afterTranslateResult = afterTranslateResult;
    }

}
