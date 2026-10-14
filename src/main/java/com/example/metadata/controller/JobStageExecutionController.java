package com.example.metadata.controller;

import com.example.metadata.dto.StartStageRequest;
import com.example.metadata.dto.UpdateStageRequest;
import com.example.metadata.entity.JobStageExecution;
import com.example.metadata.service.JobStageExecutionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs/{jobId}/stages")
public class JobStageExecutionController {

    private final JobStageExecutionService stageExecutionService;

    public JobStageExecutionController(
            JobStageExecutionService stageExecutionService) {

        this.stageExecutionService = stageExecutionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobStageExecution startStage(
            @PathVariable Long jobId,
            @RequestBody @Valid StartStageRequest request) {

        return stageExecutionService.startStage(
                jobId,
                request.getStage()
        );
    }

    @PatchMapping("/{stageExecutionId}")
    public JobStageExecution updateStage(
            @PathVariable Long jobId,
            @PathVariable Long stageExecutionId,
            @RequestBody @Valid UpdateStageRequest request) {

        return stageExecutionService.updateStage(
                jobId,
                stageExecutionId,
                request.getStatus(),
                request.getInputRecordCount(),
                request.getProcessedRecordCount(),
                request.getRejectedRecordCount(),
                request.getErrorMessage()
        );
    }

    @GetMapping
    public List<JobStageExecution> getStageHistory(
            @PathVariable Long jobId) {

        return stageExecutionService.getStageHistory(jobId);
    }
}