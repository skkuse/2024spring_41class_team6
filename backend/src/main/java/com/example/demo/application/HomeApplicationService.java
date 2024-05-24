package com.example.demo.application;

import com.example.demo.domain.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomeApplicationService {
    private final TestRepository testRepository;

    HomeApplicationService(
            TestRepository testRepository
    ) {
        this.testRepository = testRepository;
    }

    @Transactional(readOnly = true)
    public Long getTest() {
        return this.testRepository.findAll().getFirst().getId();
    }
}
