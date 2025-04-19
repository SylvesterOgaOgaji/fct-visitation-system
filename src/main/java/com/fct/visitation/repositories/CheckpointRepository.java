package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
}