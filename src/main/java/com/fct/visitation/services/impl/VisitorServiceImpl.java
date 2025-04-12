package com.fct.visitation.services.impl;

import com.fct.visitation.exceptions.DuplicateNinException;
import com.fct.visitation.exceptions.WeeklyVisitLimitException;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.interfaces.EmailService;
import com.fct.visitation.services.interfaces.NinVerificationService;
import com.fct.visitation.services.interfaces.VisitorService;
import com.fct.visitation.utils.QRCodeGeneratorInterface;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final QRCodeGeneratorInterface qrCodeGenerator;
    private final NinVerificationService ninVerificationService;
    private final EmailService emailService;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, 
                             QRCodeGeneratorInterface qrCodeGenerator,
                             NinVerificationService ninVerificationService,
                             EmailService emailService) {
        this.visitorRepository = visitorRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.ninVerificationService = ninVerificationService;
        this.emailService = emailService;
    }

    @Override
    public Visitor registerVisitor(Visitor visitor) {
        try {
            // Check if a visitor with the same email already exists
            Optional<Visitor> existingVisitorOpt = visitorRepository.findByEmail(visitor.getEmail());
            
            if (existingVisitorOpt.isPresent()) {
                Visitor existingVisitor = existingVisitorOpt.get();
                
                // Calculate the date one week ago
                LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
                
                // Check if the existing visitor's appointment was less than a week ago
                if (existingVisitor.getAppointmentDateTime().isAfter(oneWeekAgo)) {
                    throw new WeeklyVisitLimitException("You can only register for a visit once per week. Your last appointment was on " 
                        + existingVisitor.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
                // If it's been more than a week, allow the new registration
            }
            
            // Check for duplicate NIN
            Optional<Visitor> existingNinVisitor = visitorRepository.findByNin(visitor.getNin());
            if (existingNinVisitor.isPresent()) {
                throw new DuplicateNinException("A visitor with this National Identification Number already exists. Please use a different NIN.");
            }
            
            // Verify NIN with NIMC
            boolean ninVerified = ninVerificationService.verifyNin(visitor.getNin(), visitor.getFirstName(), visitor.getLastName());
            if (!ninVerified) {
                throw new RuntimeException("NIN verification failed. Please check your NIN and ensure your name matches what is on your national ID.");
            }

            // Continue with registration
            String qrCodeContent = generateQrCode(visitor);
            visitor.setQrCode(qrCodeContent);
            
            String qrCodeData = generateBase64QRCode(qrCodeContent);
            visitor.setQrCodeData(qrCodeData);
            
            // Save the visitor
            Visitor savedVisitor = visitorRepository.save(visitor);
            
            // Send confirmation email automatically
            try {
                emailService.sendVisitorPass(savedVisitor, savedVisitor.getEmail());
            } catch (Exception e) {
                // Log error but continue - we don't want to fail the registration if email fails
                System.err.println("Failed to send confirmation email: " + e.getMessage());
            }
            
            return savedVisitor;
        } catch (DataIntegrityViolationException e) {
            // Handle any other database constraint violations
            if (e.getMessage().contains("visitors.UKf9bjevs0p1csgl1xuo2vajxtw")) {
                throw new DuplicateNinException("A visitor with this National Identification Number already exists. Please use a different NIN.");
            }
            throw e;
        }
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
        // Generate a unique identifier for the QR code
        return UUID.randomUUID().toString();
    }

    @Override
    public void deleteVisitor(Long visitorId) {
        // Check if visitor exists before deleting
        visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + visitorId));
        
        // Delete the visitor
        visitorRepository.deleteById(visitorId);
    }

    // Generate Base64 encoded QR code image
    private String generateBase64QRCode(String content) {
        try {
            // Create QR code writer
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            
            // Generate QR code matrix
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(
                content, 
                BarcodeFormat.QR_CODE, 
                200,  // width
                200   // height
            );
            
            // Convert to BufferedImage
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            // Convert image to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            
            // Encode to Base64
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            // Log error (consider using a logging framework)
            System.err.println("Error generating QR code: " + e.getMessage());
            return "";
        }
    }
}