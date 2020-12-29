package com.codeup.backendchallenge.controllers;

import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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

    @PostMapping(value = "{id}/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePerson (@PathVariable(name = "id") long id) {
        personDao.deleteById(id);
    }

    //TODO: date conversion can result in being off by one day
    @PostMapping(value = "/{id}/update", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Person updatePerson(@PathVariable(name = "id") long id, @RequestBody Person person) {
        Preconditions.checkNotNull(person);
        Person personToUpdate = personDao.getOne(id);

        personToUpdate.setAge(person.getAge() == 0 ? personToUpdate.getAge() : person.getAge());
        personToUpdate.setName(person.getName() == null ? personToUpdate.getName() : person.getName());
        personToUpdate.setDateJoined(person.getDateJoined() == null ? personToUpdate.getDateJoined() : person.getDateJoined());
        personToUpdate.setDateUpdated(person.getDateUpdated() == null ? personToUpdate.getDateUpdated() : person.getDateUpdated());

        return personDao.save(personToUpdate);
    }
}
