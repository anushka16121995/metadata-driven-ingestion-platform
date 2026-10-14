package com.example.metadata.service;

import com.example.metadata.entity.IngestionJob;
import com.example.metadata.entity.JobStage;
import com.example.metadata.entity.JobStageExecution;
import com.example.metadata.entity.JobStatus;
import com.example.metadata.entity.StageStatus;
import com.example.metadata.exception.ResourceNotFoundException;
import com.example.metadata.repository.IngestionJobRepository;
import com.example.metadata.repository.JobStageExecutionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobStageExecutionService {

    private final IngestionJobRepository ingestionJobRepository;
    private final JobStageExecutionRepository stageExecutionRepository;


    public JobStageExecutionService(
            IngestionJobRepository ingestionJobRepository,
            JobStageExecutionRepository stageExecutionRepository) {

        this.ingestionJobRepository = ingestionJobRepository;
        this.stageExecutionRepository = stageExecutionRepository;
    }


    @Transactional
    public JobStageExecution startStage(
            Long jobId,
            JobStage stage) {

        // 1. Find the overall ingestion job
        IngestionJob job = ingestionJobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + jobId
                        )
                );

        if (job.getStatus() == JobStatus.FAILED
                || job.getStatus() == JobStatus.COMPLETED) {

            throw new IllegalStateException(
                    "Cannot start a stage for job in status "
                            + job.getStatus()
            );
        }


        // 2. RULE 1:
        // Do not allow another stage to start
        // while one stage is already RUNNING
        boolean runningStageExists =
                stageExecutionRepository
                        .existsByIngestionJobJobIdAndStatus(
                                jobId,
                                StageStatus.RUNNING
                        );

        if (runningStageExists) {
            throw new IllegalStateException(
                    "Another stage is already running for job " + jobId
            );
        }


        // 3. RULE 2:
        // Check that the previous required stage completed
        JobStage requiredPreviousStage = previousStage(stage);

        if (requiredPreviousStage != null) {

            boolean previousStageCompleted =
                    stageExecutionRepository
                            .existsByIngestionJobJobIdAndStageAndStatus(
                                    jobId,
                                    requiredPreviousStage,
                                    StageStatus.COMPLETED
                            );

            if (!previousStageCompleted) {
                throw new IllegalStateException(
                        requiredPreviousStage
                                + " must be completed before starting "
                                + stage
                );
            }
        }


        // 4. Determine the attempt number
        long previousAttempts =
                stageExecutionRepository
                        .countByIngestionJobJobIdAndStage(
                                jobId,
                                stage
                        );


        // 5. Create a new stage execution
        JobStageExecution execution =
                new JobStageExecution();

        execution.setIngestionJob(job);
        execution.setStage(stage);
        execution.setStatus(StageStatus.RUNNING);
        execution.setAttemptNumber((int) previousAttempts + 1);
        execution.setStartedAt(LocalDateTime.now());


        // 6. Update overall job information
        job.setStatus(JobStatus.RUNNING);
        job.setCurrentStage(stage);

        if (job.getStartedAt() == null) {
            job.setStartedAt(LocalDateTime.now());
        }


        // 7. Save the overall job
        ingestionJobRepository.save(job);


        // 8. Save and return the stage execution
        return stageExecutionRepository.save(execution);
    }


    @Transactional
    public JobStageExecution updateStage(
            Long jobId,
            Long stageExecutionId,
            StageStatus status,
            Long inputRecordCount,
            Long processedRecordCount,
            Long rejectedRecordCount,
            String errorMessage) {

        // Find the stage execution
        JobStageExecution execution =
                stageExecutionRepository.findById(stageExecutionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Stage execution not found with id: "
                                                + stageExecutionId
                                )
                        );


        // Make sure this stage execution belongs to this job
        if (!execution.getIngestionJob()
                .getJobId()
                .equals(jobId)) {

            throw new ResourceNotFoundException(
                    "Stage execution "
                            + stageExecutionId
                            + " does not belong to job "
                            + jobId
            );
        }

        if (execution.getStatus() != StageStatus.RUNNING) {

            throw new IllegalStateException(
                    "Only a RUNNING stage can be updated"
            );
        }


        // Update stage status
        execution.setStatus(status);


        // Update metrics only if values were provided
        if (inputRecordCount != null) {
            execution.setInputRecordCount(inputRecordCount);
        }

        if (processedRecordCount != null) {
            execution.setProcessedRecordCount(processedRecordCount);
        }

        if (rejectedRecordCount != null) {
            execution.setRejectedRecordCount(rejectedRecordCount);
        }

        if (errorMessage != null) {
            execution.setErrorMessage(errorMessage);
        }


        // COMPLETED or FAILED means the stage has finished
        if (status == StageStatus.COMPLETED
                || status == StageStatus.FAILED) {

            execution.setCompletedAt(LocalDateTime.now());
        }


        IngestionJob job = execution.getIngestionJob();


        // If a stage fails, fail the overall job
        if (status == StageStatus.FAILED) {

            job.setStatus(JobStatus.FAILED);
            job.setErrorMessage(errorMessage);
            job.setCompletedAt(LocalDateTime.now());
        }

        if (status == StageStatus.COMPLETED
                && execution.getStage() == JobStage.WRITER) {

            job.setStatus(JobStatus.COMPLETED);
            job.setCurrentStage(JobStage.WRITER);
            job.setCompletedAt(LocalDateTime.now());
        }


        ingestionJobRepository.save(job);

        return stageExecutionRepository.save(execution);
    }


    public List<JobStageExecution> getStageHistory(Long jobId) {

        if (!ingestionJobRepository.existsById(jobId)) {

            throw new ResourceNotFoundException(
                    "Job not found with id: " + jobId
            );
        }

        return stageExecutionRepository
                .findByIngestionJobJobIdOrderByStartedAtAsc(jobId);
    }


    private JobStage previousStage(JobStage stage) {

        return switch (stage) {

            case SCANNER -> null;

            case LOADER ->
                    JobStage.SCANNER;

            case VALIDATOR ->
                    JobStage.LOADER;

            case ENRICHER ->
                    JobStage.VALIDATOR;

            case WRITER ->
                    JobStage.ENRICHER;
        };
    }
}