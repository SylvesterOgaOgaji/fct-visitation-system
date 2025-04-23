package com.fct.visitation.services.impl;

import com.fct.visitation.exceptions.ReportGenerationException;
import com.fct.visitation.models.dto.DailyVisitationReport;
import com.fct.visitation.repositories.CheckpointLogsRepository;
import com.fct.visitation.repositories.IncidentAlertRepository;
import com.fct.visitation.repositories.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl {

    private final VisitorRepository visitorRepository;
    private final CheckpointLogsRepository checkpointLogsRepository;
    private final IncidentAlertRepository incidentAlertRepository;

    public DailyVisitationReport generateDailyVisitationReport(LocalDate date) {
        try {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(23, 59, 59);
            
            long totalVisitors = visitorRepository.countByCheckInTimeBetween(start, end);
            long entries = checkpointLogsRepository.countByEntryTimeBetweenAndStatus(
                start, end, "ENTERED");
            long exits = checkpointLogsRepository.countByExitTimeBetweenAndStatus(
                start, end, "EXITED");
            
            return new DailyVisitationReport(
                date,
                totalVisitors,
                entries,
                exits,
                entries - exits
            );
        } catch (DataAccessException e) {
            log.error("Database error generating daily report for {}", date, e);
            throw new ReportGenerationException("Failed to generate daily report", e);
        }
    }

    // Temporary placeholder implementations
    public byte[] generatePDFReport(Object data) {
        throw new UnsupportedOperationException("PDF generation not implemented");
    }

    public byte[] generateExcelReport(Object data) {
        throw new UnsupportedOperationException("Excel generation not implemented");
    }
}