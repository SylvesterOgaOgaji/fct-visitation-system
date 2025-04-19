package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.entity.VisitorStatus;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.interfaces.VisitorService;
import com.fct.visitation.utils.QRCodeGeneratorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final QRCodeGeneratorInterface qrCodeGenerator;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, 
                             QRCodeGeneratorInterface qrCodeGenerator) {
        this.visitorRepository = visitorRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @Override
    @Transactional
    public Visitor registerVisitor(Visitor visitor) {
        if (visitor.getStatus() == null) {
            visitor.setStatus(VisitorStatus.PENDING);
        }

        if (visitor.getQrCode() == null || visitor.getQrCode().isEmpty()) {
            String qrCode = qrCodeGenerator.generateQRCode(visitor);
            visitor.setQrCode(qrCode);
        }

        return visitorRepository.save(visitor);
    }

    @Override
    @Transactional
    public Visitor checkInVisitor(Long visitorId) {
        return visitorRepository.findById(visitorId)
                .map(visitor -> {
                    visitor.setStatus(VisitorStatus.CHECKED_IN);
                    visitor.setCheckedInAt(LocalDateTime.now());
                    return visitorRepository.save(visitor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with ID: " + visitorId));
    }

    @Override
    @Transactional
    public Visitor checkOutVisitor(Long visitorId) {
        return visitorRepository.findById(visitorId)
                .map(visitor -> {
                    visitor.setStatus(VisitorStatus.CHECKED_OUT);
                    visitor.setCheckedOutAt(LocalDateTime.now());
                    return visitorRepository.save(visitor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with ID: " + visitorId));
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
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with QR code: " + qrCode));
    }

    @Override
    public List<Visitor> getVisitorsWithVehicles() {
        return visitorRepository.findByCarTypeNotNull();
    }

    @Override
    public List<Visitor> getPendingVisitors() {
        return visitorRepository.findByStatus(VisitorStatus.PENDING);
    }

    @Override
    @Transactional
    public Visitor approveVisitor(Long visitorId) {
        return visitorRepository.findById(visitorId)
                .map(visitor -> {
                    visitor.setStatus(VisitorStatus.APPROVED);
                    return visitorRepository.save(visitor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with ID: " + visitorId));
    }

    @Override
    @Transactional
    public Visitor cancelVisitor(Long visitorId, String reason) {
        return visitorRepository.findById(visitorId)
                .map(visitor -> {
                    visitor.setStatus(VisitorStatus.CANCELLED);
                    visitor.setCancellationReason(reason);
                    visitor.setCancelledAt(LocalDateTime.now());
                    return visitorRepository.save(visitor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with ID: " + visitorId));
    }
}