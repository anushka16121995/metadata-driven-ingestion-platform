package com.example.metadata.dto;

import com.example.metadata.entity.StageStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStageRequest {

    @NotNull
    private StageStatus status;

    private Long inputRecordCount;

    private Long processedRecordCount;

    private Long rejectedRecordCount;

    private String errorMessage;

    public UpdateStageRequest() {
    }

    public StageStatus getStatus() {
        return status;
    }

    public void setStatus(StageStatus status) {
        this.status = status;
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
// Generate getters and setters for all fields
}