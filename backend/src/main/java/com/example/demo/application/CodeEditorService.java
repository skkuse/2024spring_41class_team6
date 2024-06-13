package com.example.demo.application;

import com.example.demo.domain.Code;
import com.example.demo.domain.CodeEditorResult;
import com.example.demo.domain.CodeMatch;
import com.example.demo.domain.ExecutionResult;
import com.example.demo.green.GreenPattern;
import org.springframework.stereotype.Service;

@Service
public class CodeEditorService {

    private final CodeService codeService;
    private final ExecutionResultService executionResultService;
    private final ExecuteApplicationService executeApplicationService;
    private final CodeMatchService codeMatchService;
    private final TranslateApplicationService translateApplicationService;
    private final GreenPattern greenPattern;

    public CodeEditorService(CodeService codeService, ExecuteApplicationService executeApplicationService,
                             ExecutionResultService executionResultService, GreenPattern greenPattern,
                             CodeMatchService codeMatchService, TranslateApplicationService translateApplicationService) {
        this.codeService = codeService;
        this.executeApplicationService = executeApplicationService;
        this.executionResultService = executionResultService;
        this.codeMatchService = codeMatchService;
        this.translateApplicationService = translateApplicationService;
        this.greenPattern = greenPattern;
    }

    // 자바 코드 저장
    public CodeEditorResult process(String code) {

        String greenCode = "";
        ExecutionResult savedExecutionResult = null;
        ExecutionResult savedGreenExecutionResult = null;
        TranslateApplicationService.TranslateResult beforeTranslateResult = null;
        TranslateApplicationService.TranslateResult afterTranslateResult = null;

        // 입력으로 받은 자바 코드 저장
        Code newCode = new Code(code);
        Code savedCode = codeService.createCode(newCode);

        // 기존 코드 실행 및 저장
        ExecutionResult newExecutionResult = executeApplicationService.run(savedCode);
        savedExecutionResult = executionResultService.createExecutionResult(newExecutionResult);

        if(newExecutionResult.getStatus().equals("SUCCESS")) {
            // 기존 코드 탄소 배출량 비교 지표 계산
            beforeTranslateResult = translateApplicationService.translate(savedExecutionResult.getEmission());

            // 개선 코드 저장
            greenCode = greenPattern.generateGreenCode(savedCode.getCode());
            Code newGreenCode = new Code(greenCode);
            Code savedGreenCode = codeService.createCode(newGreenCode);

            // CodeMatch에 매칭
            codeMatchService.createCodeMatch(new CodeMatch(savedCode.getId(), savedGreenCode.getId()));

            // 개선 코드 실행
            ExecutionResult newGreenExecutionResult = executeApplicationService.run(savedGreenCode);
            savedGreenExecutionResult = executionResultService.createExecutionResult(newGreenExecutionResult);

            // 개선 코드 탄소 배출량 비교 지표 계산
            afterTranslateResult = translateApplicationService.translate(savedGreenExecutionResult.getEmission());
        }
        else {
            // fail return
            return new CodeEditorResult(
                    greenCode,
                    savedExecutionResult,
                    savedGreenExecutionResult,
                    beforeTranslateResult,
                    afterTranslateResult
            );
        }
        // success return
        return new CodeEditorResult(
                greenCode,
                savedExecutionResult,
                savedGreenExecutionResult,
                beforeTranslateResult,
                afterTranslateResult
        );
    }
}
