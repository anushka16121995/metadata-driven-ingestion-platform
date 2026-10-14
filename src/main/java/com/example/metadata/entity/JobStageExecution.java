package com.example.metadata.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_stage_execution")
public class JobStageExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_execution_id")
    private Long stageExecutionId;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private IngestionJob ingestionJob;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = false)
    private JobStage stage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StageStatus status;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "input_record_count")
    private Long inputRecordCount;

    @Column(name = "processed_record_count")
    private Long processedRecordCount;

    @Column(name = "rejected_record_count")
    private Long rejectedRecordCount;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    public Long getStageExecutionId() {
        return stageExecutionId;
    }

    public JobStageExecution() {
    }

    public void setStageExecutionId(Long stageExecutionId) {
        this.stageExecutionId = stageExecutionId;
    }

    public IngestionJob getIngestionJob() {
        return ingestionJob;
    }

    public void setIngestionJob(IngestionJob ingestionJob) {
        this.ingestionJob = ingestionJob;
    }

    public JobStage getStage() {
        return stage;
    }

    public void setStage(JobStage stage) {
        this.stage = stage;
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Long getInputRecordCount() {
        return inputRecordCount;
    }

    public void setInputRecordCount(Long inputRecordCount) {
        this.inputRecordCount = inputRecordCount;
    }

    public Long getProcessedRecordCount() {
        return processedRecordCount;
    }

    public void setProcessedRecordCount(Long processedRecordCount) {
        this.processedRecordCount = processedRecordCount;
    }

    public Long getRejectedRecordCount() {
        return rejectedRecordCount;
    }

    public void setRejectedRecordCount(Long rejectedRecordCount) {
        this.rejectedRecordCount = rejectedRecordCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}