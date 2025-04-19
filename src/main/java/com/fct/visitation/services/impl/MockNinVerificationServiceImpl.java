package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.NinVerification;
import com.fct.visitation.repositories.NinVerificationRepository;
import com.fct.visitation.services.interfaces.NinVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Profile("test")
public class MockNinVerificationServiceImpl implements NinVerificationService {
    private final NinVerificationRepository ninVerificationRepository;

    @Autowired
    public MockNinVerificationServiceImpl(NinVerificationRepository ninVerificationRepository) {
        this.ninVerificationRepository = ninVerificationRepository;
    }

    @Override
    public Optional<NinVerification> verifyNin(String nin) {
        NinVerification mockVerification = new NinVerification();
        mockVerification.setNin(nin);
        mockVerification.setVerified(true);
        mockVerification.setVerificationTimestamp(LocalDateTime.now());
        return Optional.of(save(mockVerification));
    }

    @Override
    public Optional<NinVerification> verifyNin(String nin, Long contextId) {
        NinVerification mockVerification = new NinVerification();
        mockVerification.setNin(nin);
        mockVerification.setContextId(contextId);
        mockVerification.setVerified(true);
        mockVerification.setVerificationTimestamp(LocalDateTime.now());
        return Optional.of(save(mockVerification));
    }

    @Override
    public Optional<NinVerification> findById(Long id) {
        return ninVerificationRepository.findById(id);
    }

    @Override
    public NinVerification save(NinVerification ninVerification) {
        return ninVerificationRepository.save(ninVerification);
    }

    @Override
    public Optional<NinVerification> validateNinDetails(String nin, String firstName, String lastName) {
        NinVerification mockVerification = new NinVerification();
        mockVerification.setNin(nin);
        
        // Mock validation logic
        boolean isValid = nin != null && !nin.isEmpty() && 
                          firstName != null && !firstName.isEmpty() && 
                          lastName != null && !lastName.isEmpty();
        
        mockVerification.setVerified(isValid);
        mockVerification.setVerificationTimestamp(LocalDateTime.now());
        
        return Optional.of(save(mockVerification));
    }
}