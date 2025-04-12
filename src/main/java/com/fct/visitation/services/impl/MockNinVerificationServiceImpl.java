package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.NinVerification;
import com.fct.visitation.repositories.NinVerificationRepository;
import com.fct.visitation.services.interfaces.NinVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Primary
public class MockNinVerificationServiceImpl implements NinVerificationService {

    private final NinVerificationRepository ninVerificationRepository;

    @Autowired
    public MockNinVerificationServiceImpl(NinVerificationRepository ninVerificationRepository) {
        this.ninVerificationRepository = ninVerificationRepository;
    }
 @Override
    public boolean verifyNin(String nin, String firstName, String lastName) {
        Optional<NinVerification> verificationOpt = ninVerificationRepository.findByNin(nin);
        
        if (verificationOpt.isEmpty()) {
            return false; // NIN not found
        }
        
        NinVerification verification = verificationOpt.get();
        
        // Check if the first name and last name match (case insensitive)
        boolean nameMatches = verification.getFirstName().equalsIgnoreCase(firstName) 
                && verification.getLastName().equalsIgnoreCase(lastName);
 // Return true if the names match and the NIN is verified
        return nameMatches && verification.isVerified();
    }
    
    @Override
    public NinVerification getNinDetails(String nin) {
        return ninVerificationRepository.findByNin(nin).orElse(null);
    }
}
