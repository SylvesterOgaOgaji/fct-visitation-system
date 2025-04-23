package com.fct.visitation.models.dto;

import java.time.LocalDate;

public record DailyVisitationReport(
    LocalDate date,
    long totalVisitors,
    long entries,
    long exits,
    long activeVisitors
) {}