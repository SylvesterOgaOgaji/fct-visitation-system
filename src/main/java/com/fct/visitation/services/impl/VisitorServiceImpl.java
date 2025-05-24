package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.enums.VisitorStatus;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.interfaces.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository = null;

    @Override
    @Transactional
    public Visitor registerVisitor(Visitor visitor) {
        visitor.setRegistrationTime(LocalDateTime.now());
        visitor.setStatus(VisitorStatus.PENDING);
        return visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public Visitor checkInVisitor(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitor.setStatus(VisitorStatus.CHECKED_IN);
        visitor.setCheckInTime(LocalDateTime.now());
        return visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public Visitor checkOutVisitor(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitor.setStatus(VisitorStatus.CHECKED_OUT);
        visitor.setCheckOutTime(LocalDateTime.now());
        return visitorRepository.save(visitor);
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    @Override
    public Optional<Visitor> getVisitorById(Long id) {
        return visitorRepository.findById(id);
    }

    @Override
    @Transactional
    public Visitor saveVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public void deleteVisitor(Long id) {
        visitorRepository.deleteById(id);
    }

    @Override
    public List<Visitor> findVisitorsByName(String name) {
        return visitorRepository.findByFirstNameContainingOrLastNameContaining(name, name);
    }

    @Override
    public Optional<Visitor> findVisitorByEmail(String email) {
        return visitorRepository.findByEmail(email);
    }

    @Override
    public Optional<Visitor> findVisitorByPhone(String phone) {
        return visitorRepository.findByPhoneNumber(phone);
    }

    @Override
    public List<Visitor> findCurrentVisitors() {
        return visitorRepository.findByStatus(VisitorStatus.CHECKED_IN);
    }

    @Override
    public Optional<Visitor> getVisitorByQrCode(String qrCode) {
        return visitorRepository.findByQrCode(qrCode);
    }

    @Override
    public Visitor findVisitorByQRCode(String qrCode) {
        return visitorRepository.findByQrCode(qrCode)
            .orElseThrow(() -> new RuntimeException("Visitor not found with QR code"));
    }

    @Override
    public List<Visitor> getVisitorsWithVehicles() {
        return visitorRepository.findByVehicleIsNotNull();
    }

    @Override
    public List<Visitor> getPendingVisitors() {
        return visitorRepository.findByStatus(VisitorStatus.PENDING);
    }

    @Override
    @Transactional
    public Visitor approveVisitor(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitor.setStatus(VisitorStatus.APPROVED);
        return visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public Visitor cancelVisitor(Long visitorId, String reason) {
        Visitor visitor = visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitor.setStatus(VisitorStatus.CANCELLED);
        visitor.setCancellationReason(reason);
        return visitorRepository.save(visitor);
    }
}