package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Visitor;
import java.util.List;
import java.util.Optional;

public interface VisitorService {
    // Visitor Registration & Check-in/out
    Visitor registerVisitor(Visitor visitor);
    Visitor checkInVisitor(Long visitorId);
    Visitor checkOutVisitor(Long visitorId);

    // CRUD Operations
    List<Visitor> getAllVisitors();
    Optional<Visitor> getVisitorById(Long id);
    Visitor saveVisitor(Visitor visitor);
    void deleteVisitor(Long id);

    // Query Methods
    List<Visitor> findVisitorsByName(String name);
    Optional<Visitor> findVisitorByEmail(String email);
    Optional<Visitor> findVisitorByPhone(String phone);
    List<Visitor> findCurrentVisitors();
    Optional<Visitor> getVisitorByQrCode(String qrCode);
    Visitor findVisitorByQRCode(String qrCode);
    List<Visitor> getVisitorsWithVehicles();

    // Approval Workflow
    List<Visitor> getPendingVisitors();
    Visitor approveVisitor(Long visitorId);
    Visitor cancelVisitor(Long visitorId, String reason);
}