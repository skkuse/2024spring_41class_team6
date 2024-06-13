package com.example.demo.application;

import com.example.demo.domain.Code;
import com.example.demo.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    /*
     *  새로운 Code를 저장하고 반환
     *  param: 새로 생성 후 저장할 Code 정보
     *  return: 생성된 Code 정보
     */
    public Code createCode(Code code) {
        return codeRepository.save(code);
    }

    /*
     *  Code 테이블의 Id로 Code 정보 조회
     *  param: 조회 할 Code 의 Id
     *  return: Code의 정보가 존재하면 Code의 정보를 반환, 없다면 null 반환
     */
    public Optional<Code> findById(int codeId) {
        return codeRepository.findById(codeId);
    }

    /*
     *  특정 repo_id에 속하는 모든 Code들을 조회
     *  param: 특정 GitHub repository의 repo_id
     *  return: Code들의 List를 반환
     */
    public List<Code> getAllCodesByRepoId(int repoId) {
        return codeRepository.findByRepoId(repoId);
    }
}
