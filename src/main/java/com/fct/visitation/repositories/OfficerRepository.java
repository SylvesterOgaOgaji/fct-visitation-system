package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {
    List<Officer> findByFacilityFacilityId(Long facilityId);
}
