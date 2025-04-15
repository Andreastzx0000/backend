package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    List<Participation> findByPersonIdAndStatus(Integer personId, String status);
    List<Participation> findByEventIdAndStatus(Integer eventId, String status);
    Participation findByPersonIdAndEventId(Integer personId, Integer eventId);
}
