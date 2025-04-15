package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Person;
import com.ass.citysparkapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Optional<Person> getPersonById(@PathVariable Integer id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/user/{userId}")
    public Person getPersonByUserId(@PathVariable Integer userId) {
        return personService.getPersonByUserId(userId);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Integer id, @RequestBody Person updatedPerson) {
        Optional<Person> existing = personService.getPersonById(id);
        if (existing.isPresent()) {
            updatedPerson.setId(id);
            return personService.savePerson(updatedPerson);
        } else {
            throw new RuntimeException("Person not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
    }
}
