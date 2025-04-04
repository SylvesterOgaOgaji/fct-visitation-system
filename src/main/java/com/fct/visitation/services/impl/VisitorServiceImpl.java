package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.interfaces.VisitorService;
import com.fct.visitation.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, QRCodeGenerator qrCodeGenerator) {
        this.visitorRepository = visitorRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @Override
    public Visitor registerVisitor(Visitor visitor) {
        visitor.setQrCode(generateQrCode(visitor));
        return visitorRepository.save(visitor);
    }

    @Override
    public Optional<Visitor> findById(Long visitorId) {
        return visitorRepository.findById(visitorId);
    }

    @Override
    public Optional<Visitor> findByQrCode(String qrCode) {
        return visitorRepository.findByQrCode(qrCode);
    }

    @Override
    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    @Override
    public List<Visitor> findByStatus(Visitor.VisitorStatus status) {
        return visitorRepository.findByStatus(status);
    }

    @Override
    public Visitor checkInVisitor(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setStatus(Visitor.VisitorStatus.CHECKED_IN);
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor completeVisit(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setStatus(Visitor.VisitorStatus.COMPLETED);
        return visitorRepository.save(visitor);
    }

    @Override
    public String generateQrCode(Visitor visitor) {
        String uniqueId = UUID.randomUUID().toString();
        String qrContent = "FCT-VISIT:" + visitor.getVisitorId() + ":" + uniqueId;
        return qrContent;
    }

    @Override
    public void deleteVisitor(Long visitorId) {
        visitorRepository.deleteById(visitorId);
    }
}
