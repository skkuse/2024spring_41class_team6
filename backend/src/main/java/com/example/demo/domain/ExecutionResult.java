package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "execution_result")
public class ExecutionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code_id")
    private int codeId;

    @Column(name = "status")
    private String status;

    @Column(name = "runtime")
    private Long runtime;

    @Column(name = "memory")
    private Long memory;

    @Column(name = "emission")
    private float emission;

    @Transient
    private String output;

    public ExecutionResult(int codeId, String status, Long runtime, Long memory, float emission, String output) {
        this.codeId = codeId;
        this.status = status;
        this.runtime = runtime;
        this.memory = memory;
        this.emission = emission;
        this.output = output;
    }
}
