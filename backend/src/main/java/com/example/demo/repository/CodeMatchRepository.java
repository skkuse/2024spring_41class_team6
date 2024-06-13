package com.example.demo.repository;

import com.example.demo.domain.CodeMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeMatchRepository extends JpaRepository<CodeMatch, Integer> {

    Optional<CodeMatch> findByBeforeId(int beforeId);
}
