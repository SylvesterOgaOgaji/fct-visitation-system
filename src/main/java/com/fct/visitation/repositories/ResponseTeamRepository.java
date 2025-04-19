package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.ResponseTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseTeamRepository extends JpaRepository<ResponseTeam, Long> {
    ResponseTeam findByTeamName(String teamName);
}