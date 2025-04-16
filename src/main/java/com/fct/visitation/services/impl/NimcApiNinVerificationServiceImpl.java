package com.fct.visitation.services.impl;

import com.fct.visitation.exceptions.NinVerificationException;
import com.fct.visitation.models.entity.NinVerification;
import com.fct.visitation.repositories.NinVerificationRepository;
import com.fct.visitation.services.interfaces.NinVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the NIN verification service that connects to the actual NIMC API
 * for NIN verification using the eNVS API.
 */
@Service
public class NimcApiNinVerificationServiceImpl implements NinVerificationService {

    private final RestTemplate restTemplate;
    private final NinVerificationRepository ninVerificationRepository;

    @Value("${nimc.api.url}")
    private String nimcApiUrl;

    @Value("${nimc.api.key}")
    private String nimcApiKey;

    @Value("${nimc.api.secret}")
    private String nimcApiSecret;

    @Autowired
    public NimcApiNinVerificationServiceImpl(
            RestTemplate restTemplate,
            NinVerificationRepository ninVerificationRepository) {
        this.restTemplate = restTemplate;
        this.ninVerificationRepository = ninVerificationRepository;
    }

    @Override
    public NinVerification verifyNin(String nin, String firstName, String lastName) {
        // Check if we already have a verification for this NIN
        NinVerification existingVerification = ninVerificationRepository.findByNin(nin);
        
        if (existingVerification != null && existingVerification.isValid() && !existingVerification.isExpired()) {
            return existingVerification;
        }

        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-KEY", nimcApiKey);
            headers.set("Authorization", "Bearer " + nimcApiSecret);
            headers.set("Content-Type", "application/json");

            // Prepare request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("nin", nin);
            requestBody.put("firstName", firstName);
            requestBody.put("lastName", lastName);

            // Create the request entity
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Make the API call
            ResponseEntity<Map> response = restTemplate.exchange(
                    nimcApiUrl + "/verify",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Process the response
            Map<String, Object> responseBody = response.getBody();
            
            if (responseBody != null && "success".equals(responseBody.get("status"))) {
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                
                // Create or update the NIN verification record
                NinVerification verification = existingVerification != null ? 
                        existingVerification : new NinVerification();
                
                verification.setNin(nin);
                verification.setFirstName((String) data.get("firstName"));
                verification.setLastName((String) data.get("lastName"));
                verification.setMiddleName((String) data.get("middleName"));
                verification.setGender((String) data.get("gender"));
                verification.setDateOfBirth((String) data.get("dateOfBirth"));
                verification.setPhoneNumber((String) data.get("phoneNumber"));
                verification.setAddress((String) data.get("residentialAddress"));
                verification.setPhoto((String) data.get("photo"));
                verification.setSignature((String) data.get("signature"));
                verification.setValid(true);
                verification.setVerificationDate(LocalDateTime.now());
                verification.setExpiryDate(LocalDateTime.now().plusDays(30)); // Valid for 30 days
                
                return ninVerificationRepository.save(verification);
            } else {
                String errorMessage = responseBody != null && responseBody.get("message") != null ? 
                        (String) responseBody.get("message") : "NIN verification failed";
                
                throw new NinVerificationException(errorMessage);
            }
        } catch (RestClientException e) {
            throw new NinVerificationException("Error connecting to NIMC API: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new NinVerificationException("Unexpected error during NIN verification: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateNinDetails(String nin, String firstName, String lastName) {
        try {
            NinVerification verification = verifyNin(nin, firstName, lastName);
            return verification != null && verification.isValid() && 
                   verification.getFirstName().equalsIgnoreCase(firstName) && 
                   verification.getLastName().equalsIgnoreCase(lastName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public NinVerification getVerificationByNin(String nin) {
        return ninVerificationRepository.findByNin(nin);
    }
}