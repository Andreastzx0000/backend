package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByStatus(String status);
    List<Event> findByTagId(Integer tagId);

    @Query("""
    SELECT e FROM Event e
    WHERE e.status = 'A'
    AND (:location IS NULL OR e.location LIKE %:location%)
    AND (:start IS NULL OR e.eventStartDt >= :start)
    AND (:end IS NULL OR e.eventEndDt <= :end)
    AND (:tagId IS NULL OR e.tag.id = :tagId)
    AND (:keyword IS NULL OR e.title LIKE %:keyword% OR e.description LIKE %:keyword%)
    AND e.tag.status = 'A'
""")
    List<Event> filterEvents(
            @Param("location") String location,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("tagId") Integer tagId,
            @Param("keyword") String keyword
    );

}
