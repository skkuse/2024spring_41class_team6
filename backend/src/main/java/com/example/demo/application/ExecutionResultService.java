package com.example.demo.application;

import com.example.demo.domain.ExecutionResult;
import com.example.demo.repository.ExecutionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecutionResultService {

    private final ExecutionResultRepository executionResultRepository;

    public ExecutionResultService(ExecutionResultRepository executionResultRepository) {
        this.executionResultRepository = executionResultRepository;
    }

    /*
     *  새로운 ExecutionResult를 저장하고 반환
     *  param: 새로 생성 후 저장할 ExecutionResult 정보
     *  return: 생성된 ExecutionResult 정보
     */
    public ExecutionResult createExecutionResult(ExecutionResult executionResult) {
        return executionResultRepository.save(executionResult);
    }

    /*
     *  특정 Code의 ExecutionResult를 반환
     *  param: ExecutionResult를 조회할 Code의 Id
     *  return: ExecutionResult의 정보가 존재하면 ExecutionResult의 정보를 반환, 없다면 null 반환
     */
    public Optional<ExecutionResult> findByCodeId(int codeId) {
        return executionResultRepository.findByCodeId(codeId);
    }
}
