package com.example.demo.repository;

import com.example.demo.domain.ExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Integer> {

    Optional<ExecutionResult> findByCodeId(int codeId);
}
