package com.example.demo.repository;

import com.example.demo.domain.Repo;
import com.example.demo.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Integer> {

}
