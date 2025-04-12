package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.NinVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NinVerificationRepository extends JpaRepository<NinVerification, String> {
    Optional<NinVerification> findByNin(String nin);
}
