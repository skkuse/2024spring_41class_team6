package com.example.demo.application;

import com.example.demo.domain.CodeMatch;
import com.example.demo.repository.CodeMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodeMatchService {

    @Autowired
    private CodeMatchRepository codeMatchRepository;

    /*
     *  새로운 CodeMatch를 저장하고 반환
     *  param: 새로 생성 후 저장할 CodeMatch 정보
     *  return: 생성된 CodeMatch 정보
     */
    public CodeMatch createCodeMatch(CodeMatch codeMatch) {
        return codeMatchRepository.save(codeMatch);
    }

    /*
     *  Green Pattern 적용 전 Code의 Id를 사용하여 CodeMatch 조회
     *  param: Green Pattern 적용 전 Code의 Id
     *  return: CodeMatch의 정보가 존재하면 CodeMatch의 정보를 반환, 없다면 null 반환
     */
    public Optional<CodeMatch> findByBeforeId(int beforeId) {
        return codeMatchRepository.findByBeforeId(beforeId);
    }
}
