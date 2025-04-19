package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.NinVerification;
import com.fct.visitation.repositories.NinVerificationRepository;
import com.fct.visitation.services.interfaces.NinVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Profile("production")
public class NimcApiNinVerificationServiceImpl implements NinVerificationService {
    private final NinVerificationRepository ninVerificationRepository;

    @Autowired
    public NimcApiNinVerificationServiceImpl(
            NinVerificationRepository ninVerificationRepository,
            RestTemplate restTemplate) {
        this.ninVerificationRepository = ninVerificationRepository;
    }

    @Override
    public Optional<NinVerification> verifyNin(String nin) {
        NinVerification verification = new NinVerification();
        verification.setNin(nin);
        verification.setVerified(validateWithNimcApi(nin));
        verification.setVerificationTimestamp(LocalDateTime.now());
        return Optional.of(save(verification));
    }

    @Override
    public Optional<NinVerification> verifyNin(String nin, Long contextId) {
        NinVerification verification = new NinVerification();
        verification.setNin(nin);
        verification.setContextId(contextId);
        verification.setVerified(validateWithNimcApi(nin));
        verification.setVerificationTimestamp(LocalDateTime.now());
        return Optional.of(save(verification));
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
        // Implement comprehensive NIN validation
        NinVerification verification = new NinVerification();
        verification.setNin(nin);
        
        // In a real-world scenario, you would call an actual NIMC API to validate these details
        boolean isValid = validateWithNimcApi(nin) && 
                          validateNameWithNimcApi(nin, firstName, lastName);
        
        verification.setVerified(isValid);
        verification.setVerificationTimestamp(LocalDateTime.now());
        
        return Optional.of(save(verification));
    }

    private boolean validateWithNimcApi(String nin) {
        // Placeholder for actual NIMC API verification
        try {
            // Simulated validation logic
            return nin != null && nin.length() == 11;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateNameWithNimcApi(String nin, String firstName, String lastName) {
        // Placeholder for name validation with NIMC API
        try {
            // Simulated name validation logic
            return firstName != null && !firstName.isEmpty() && 
                   lastName != null && !lastName.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}