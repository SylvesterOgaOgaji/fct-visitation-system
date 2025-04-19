package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.NinVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NinVerificationRepository extends JpaRepository<NinVerification, Long> {
    /**
     * Find NIN verification by NIN number
     * @param nin National Identification Number
     * @return Optional of NinVerification
     */
    Optional<NinVerification> findByNin(String nin);

    /**
     * Find NIN verification by NIN and context ID
     * @param nin National Identification Number
     * @param contextId Context ID
     * @return Optional of NinVerification
     */
    Optional<NinVerification> findByNinAndContextId(String nin, Long contextId);
}