package com.codeup.backendchallenge.controllers;

import com.codeup.backendchallenge.models.Job;
import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/persons")
public class PersonController {

    private final PersonRepository personDao;

    //get single entry
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person getPerson(@PathVariable(name = "id") long id) {
        return personDao.getOne(id);
    }

    //delete single person
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable(name = "id") long id) {
        personDao.deleteById(id);
    }

    //update single person
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@PathVariable(name = "id") long id, @RequestBody Person person) {
        Preconditions.checkNotNull(person);
        personDao.save(person);
    }

    //get all persons
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> getAll() {
        return personDao.findAll();
    }

    //create person
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) {
        Preconditions.checkNotNull(person);
        return personDao.save(person);
    }

    //update only the job of a person
    @PatchMapping(value = "/{id}/jobs", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePersonJob(@PathVariable(name = "id") long id, @RequestBody Job job) {
        Person person = personDao.getOne(id);

        person.setJob(job);
        personDao.save(person);
    }

}
