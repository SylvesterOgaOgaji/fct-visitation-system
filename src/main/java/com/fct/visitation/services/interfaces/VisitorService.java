package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Visitor;
import java.util.List;
import java.util.Optional;

public interface VisitorService {
    Visitor registerVisitor(Visitor visitor);
    Optional<Visitor> findById(Long visitorId);
    Optional<Visitor> findByQrCode(String qrCode);
    List<Visitor> findAll();
    List<Visitor> findByStatus(Visitor.VisitorStatus status);
    Visitor checkInVisitor(Long visitorId);
    Visitor completeVisit(Long visitorId);
    String generateQrCode(Visitor visitor);
    void deleteVisitor(Long visitorId);
}
