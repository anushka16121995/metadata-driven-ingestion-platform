package com.example.metadata.repository;

import com.example.metadata.entity.JobStage;
import com.example.metadata.entity.JobStageExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.metadata.entity.StageStatus;

public interface JobStageExecutionRepository
        extends JpaRepository<JobStageExecution, Long> {

    long countByIngestionJobJobIdAndStage(
            Long jobId,
            JobStage stage
    );

    List<JobStageExecution>
    findByIngestionJobJobIdOrderByStartedAtAsc(Long jobId);

    boolean existsByIngestionJobJobIdAndStatus(
            Long jobId,
            StageStatus status
    );

    boolean existsByIngestionJobJobIdAndStageAndStatus(
            Long jobId,
            JobStage stage,
            StageStatus status
    );
}