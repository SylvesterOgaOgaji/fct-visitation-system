package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.IncidentAlert;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentAlertRepository extends JpaRepository<IncidentAlert, Long> {
    List<IncidentAlert> findByStatus(IncidentAlert.Status status);
    List<IncidentAlert> findByStatusIn(List<IncidentAlert.Status> statuses);
    List<IncidentAlert> findByVisitor_Id(Long visitorId);
    List<IncidentAlert> findByReportedBy(String reportedBy);
    List<IncidentAlert> findByAssignedResponseTeamTeamId(Long teamId);
    List<IncidentAlert> findByReportedAtBetween(LocalDateTime start, LocalDateTime end);
}