package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.CheckpointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface CheckpointLogsRepository extends JpaRepository<CheckpointLog, String> {
    long countByEntryTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
    long countByExitTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
}