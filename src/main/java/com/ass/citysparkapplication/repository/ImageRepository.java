package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByEventId(Integer eventId);
}
