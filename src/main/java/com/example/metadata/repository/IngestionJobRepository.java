package com.example.metadata.repository;

import com.example.metadata.entity.IngestionJob;
import com.example.metadata.entity.JobStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngestionJobRepository
        extends JpaRepository<IngestionJob, Long> {

    IngestionJob findByFileName (String fileName);

    IngestionJob findByFileNameAndFileSizeBytes (String fileName, Long size);

    @Query("""
       SELECT ij
       FROM IngestionJob ij
       WHERE ij.fileSizeBytes BETWEEN :start AND :end
       """)
    List<IngestionJob> findBetweenSize(
            @Param("start") Long start,
            @Param("end") Long end
    );

    @Query("""
       SELECT ij
       FROM IngestionJob ij
       WHERE ij.fileSizeBytes BETWEEN :start AND :middle or ij.fileSizeBytes BETWEEN :middle AND :end
       """)
    List<IngestionJob> findBetweenSize(
            @Param("start") Long start,
            @Param("middle") Long middle,
            @Param("end") Long end
    );

    @Query("""
       SELECT ij
       FROM IngestionJob ij
       WHERE ij.fileSizeBytes BETWEEN :start AND :middle or ij.fileSizeBytes BETWEEN :middle AND :end
       """)
    List<IngestionJob> findBetweenSize(
            @Param("start") Long start,
            @Param("middle") String middle,
            @Param("end") Long end
    );

    @Query("""
       SELECT COUNT(ij)
       FROM IngestionJob ij
       """)
    Long countJobs();
}