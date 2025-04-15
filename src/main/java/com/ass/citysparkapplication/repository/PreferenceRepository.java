package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Integer> {
    List<Preference> findByPersonId(Integer personId);
    void deleteByPersonId(Integer personId);
}
