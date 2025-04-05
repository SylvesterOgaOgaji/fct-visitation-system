package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.interfaces.VisitorService;
import com.fct.visitation.utils.QRCodeGeneratorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final QRCodeGeneratorInterface qrCodeGenerator;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, QRCodeGeneratorInterface qrCodeGenerator) {
        this.visitorRepository = visitorRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @Override
    public Visitor registerVisitor(Visitor visitor) {
        String qrCode = generateQrCode(visitor);
        visitor.setQrCode(qrCode);
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
        return visitorRepository.findAll().stream()
            .filter(visitor -> visitor.getStatus() == status)
            .collect(Collectors.toList());
    }

    @Override
    public Visitor checkInVisitor(Long visitorId) {
        Visitor visitor = findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setStatus(Visitor.VisitorStatus.CHECKED_IN);
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor completeVisit(Long visitorId) {
        Visitor visitor = findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setStatus(Visitor.VisitorStatus.COMPLETED);
        return visitorRepository.save(visitor);
    }

    @Override
    public String generateQrCode(Visitor visitor) {
        String qrCodeData = UUID.randomUUID().toString();
        return qrCodeGenerator.generateQRCodeImage(qrCodeData, 200, 200);
    }

    @Override
    public void deleteVisitor(Long visitorId) {
        visitorRepository.deleteById(visitorId);
    }
}
