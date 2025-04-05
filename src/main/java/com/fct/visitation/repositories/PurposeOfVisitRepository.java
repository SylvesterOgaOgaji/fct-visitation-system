package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.PurposeOfVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeOfVisitRepository extends JpaRepository<PurposeOfVisit, Long> {
}
