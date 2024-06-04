package com.example.demo.repository;

import com.example.demo.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    Optional<Code> findById(int codeId);

    List<Code> findByRepoId(int repoId);
}
