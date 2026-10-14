package com.example.metadata.controller;

import com.example.metadata.dto.CreateJobRequest;
import com.example.metadata.entity.IngestionJob;
import com.example.metadata.service.IngestionJobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/jobs")
public class IngestionJobController {

    private final IngestionJobService ingestionJobService;

    public IngestionJobController(IngestionJobService ingestionJobService) {
        this.ingestionJobService = ingestionJobService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngestionJob createJob(
            @RequestBody @Valid CreateJobRequest request) {

        return ingestionJobService.createJob(
                request.getFileName(),
                request.getInputFilePath(),
                request.getFileSizeBytes()
        );
    }

    @GetMapping("/{jobId}")
    public IngestionJob getJob(
            @PathVariable Long jobId) {

        return ingestionJobService.getJob(jobId);
    }
}