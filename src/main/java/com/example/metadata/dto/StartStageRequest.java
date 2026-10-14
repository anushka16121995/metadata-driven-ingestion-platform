package com.example.metadata.dto;

import com.example.metadata.entity.JobStage;
import jakarta.validation.constraints.NotNull;

public class StartStageRequest {

    @NotNull
    private JobStage stage;

    public StartStageRequest() {
    }

    public JobStage getStage() {
        return stage;
    }

    public void setStage(JobStage stage) {
        this.stage = stage;
    }
}