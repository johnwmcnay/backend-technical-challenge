package com.codeup.backendchallenge.controllers;

import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.PersonRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/person")
public class PersonController {


    private final PersonRepository personDao;

    @GetMapping("/{id}/get")
    public Person getPerson(@PathVariable(name = "id") long id) {

        return personDao.getOne(id);
    }


}
