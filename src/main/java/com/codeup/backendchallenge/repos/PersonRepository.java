package com.codeup.backendchallenge.repos;

import com.codeup.backendchallenge.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
}
