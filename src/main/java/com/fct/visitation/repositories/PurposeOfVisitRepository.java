package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.PurposeOfVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurposeOfVisitRepository extends JpaRepository<PurposeOfVisit, Long> {
    List<PurposeOfVisit> findByFacilityFacilityId(Long facilityId);
}
