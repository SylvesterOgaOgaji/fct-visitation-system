package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.IncidentAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentAlertRepository extends JpaRepository<IncidentAlert, Long> {
    List<IncidentAlert> findByStatus(IncidentAlert.Status status);
    List<IncidentAlert> findByStatusIn(List<IncidentAlert.Status> statuses);
    List<IncidentAlert> findByVisitorVisitorId(Long visitorId);
    List<IncidentAlert> findByReportedBySecurityId(Long securityId);
    List<IncidentAlert> findByResponseTeamTeamId(Long teamId);
    List<IncidentAlert> findByTimeReportedBetween(LocalDateTime start, LocalDateTime end);
}