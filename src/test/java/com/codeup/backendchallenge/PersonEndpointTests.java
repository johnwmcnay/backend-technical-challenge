package com.codeup.backendchallenge;

import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendChallengeApplication.class)
@AutoConfigureMockMvc
public class PersonEndpointTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonRepository personDao;

    @Before
    public void setup() {

        Person person = personDao.findByName("Test Name");

        if (person == null) {
            Person newPerson = new Person(
                    "Test Name", 45, Date.valueOf("2011-02-03"), Date.valueOf("2012-04-05"));
            personDao.save(newPerson);
        }
    }

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testAddPerson() throws Exception {

        Person person = new Person("Another One", 82, Date.valueOf("2005-05-05"), Date.valueOf("2009-07-07"));

        ObjectMapper obj = new ObjectMapper();
        String jsonStr = obj.writeValueAsString(person);

        this.mvc.perform(post("/person/add")
                .contentType("application/json")
                .content(jsonStr))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAllPersons() throws Exception {
        List<Person> existingPersons = personDao.findAll();

        this.mvc.perform(get("/person/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(existingPersons.size())))
                // Test the static content of the page
                .andExpect(content().contentType("application/json"));
    }
}