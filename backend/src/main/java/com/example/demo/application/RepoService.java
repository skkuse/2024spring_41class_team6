package com.example.demo.application;

import com.example.demo.domain.Repo;
import com.example.demo.repository.RepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepoService {

    @Autowired
    private RepoRepository repoRepository;

    /*
     *  새로운 Repo를 저장하고 반환
     *  param: 새로 생성 후 저장할 Repo 정보
     *  return: 생성된 Repo 정보
     */
    public Repo createRepo(Repo repo) {
        return repoRepository.save(repo);
    }
}
