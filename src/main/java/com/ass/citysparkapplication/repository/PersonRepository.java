package com.ass.citysparkapplication.repository;

import com.ass.citysparkapplication.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUserId(Integer userId);
}
