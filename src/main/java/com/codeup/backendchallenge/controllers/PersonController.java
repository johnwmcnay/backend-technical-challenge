package com.codeup.backendchallenge.controllers;

import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonRepository personDao;

    @GetMapping("/{id}/get")
    public Person getPerson(@PathVariable(name = "id") long id) {

        return personDao.getOne(id);
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personDao.findAll();
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) {
        Preconditions.checkNotNull(person);
        return personDao.save(person);
    }

    //update

    //delete

}
