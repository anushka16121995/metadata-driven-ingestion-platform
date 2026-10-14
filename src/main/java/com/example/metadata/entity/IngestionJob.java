package com.example.metadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "ingestion_job")
public class IngestionJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "input_file_path", nullable = false)
    private String inputFilePath;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage")
    private JobStage currentStage;

    @Column(name = "input_record_count")
    private Long inputRecordCount;

    @Column(name = "processed_record_count")
    private Long processedRecordCount;

    @Column(name = "rejected_record_count")
    private Long rejectedRecordCount;

    @Column(name = "output_path")
    private String outputPath;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    public Long getJobId() {
        return jobId;
    }

    public IngestionJob() {
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(JobStage currentStage) {
        this.currentStage = currentStage;
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

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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