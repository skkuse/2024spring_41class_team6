package com.example.demo.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Getter;

@Entity
@Getter
@Table(name = "repo")
public class Repo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "commit_time")
    private Timestamp commitTime;

}
