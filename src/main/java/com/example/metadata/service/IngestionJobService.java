package com.example.metadata.service;

import com.example.metadata.entity.IngestionJob;
import com.example.metadata.entity.JobStatus;
import com.example.metadata.repository.IngestionJobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.metadata.exception.ResourceNotFoundException;

@Service
public class IngestionJobService {

    private final IngestionJobRepository ingestionJobRepository;

    public IngestionJobService(IngestionJobRepository ingestionJobRepository) {
        this.ingestionJobRepository = ingestionJobRepository;
    }

    public IngestionJob createJob(
            String fileName,
            String inputFilePath,
            Long fileSizeBytes) {

        IngestionJob job = new IngestionJob();

        job.setFileName(fileName);
        job.setInputFilePath(inputFilePath);
        job.setFileSizeBytes(fileSizeBytes);

        job.setStatus(JobStatus.CREATED);
        job.setCreatedAt(LocalDateTime.now());

        job.setInputRecordCount(0L);
        job.setProcessedRecordCount(0L);
        job.setRejectedRecordCount(0L);

        return ingestionJobRepository.save(job);
    }

    public IngestionJob getJob(Long jobId) {

        return ingestionJobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + jobId));
    }


}