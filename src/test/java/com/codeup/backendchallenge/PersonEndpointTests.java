package com.codeup.backendchallenge;

import com.codeup.backendchallenge.models.Job;
import com.codeup.backendchallenge.models.Person;
import com.codeup.backendchallenge.repos.JobRepository;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    JobRepository jobDao;

    @Before
    public void setup() {

        Person person = personDao.findByName("Test Name");
        Job job = jobDao.findByJobTitle("Temporary Test");

        if (job == null) {
            Job newJob = new Job("Temporary Test", 45678);
            jobDao.save(newJob);
        }

        if (person == null) {
            Person newPerson = new Person(
                    "Test Name", 45,
                    Date.valueOf("2011-02-03"), Date.valueOf("2012-04-05"),
                    jobDao.findByJobTitle("Temporary Test"));
            personDao.save(newPerson);
        }
    }

    @After
    public void postTest() {
        Person person = personDao.findByName("Test Name");
        Job job = jobDao.findByJobTitle("Temporary Test");

        if (person != null) {
            personDao.delete(person);
        }

        if (job != null) {
            jobDao.delete(job);
        }
    }

    @Test
    public void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    public void testAddPerson() throws Exception {

        Person person = new Person("Another One", 82, Date.valueOf("2005-05-05"), Date.valueOf("2009-07-07"), jobDao.findByJobTitle("Temporary Test"));
        ObjectMapper obj = new ObjectMapper();
        String jsonStr = obj.writeValueAsString(person);

        this.mvc.perform(post("/persons")
                .contentType("application/json")
                .content(jsonStr))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['name']", is(person.getName())))
                .andExpect(jsonPath("$['age']", is(person.getAge())))
                .andExpect(jsonPath("$['job'].id", is((int) person.getJob().getId())))
                .andExpect(jsonPath("$['dateJoined']", is(person.getDateJoined().toString())))
                .andExpect(jsonPath("$['dateUpdated']", is(person.getDateUpdated().toString())));

        personDao.delete(personDao.findByName(person.getName()));
    }

    @Test
    public void testAllPersons() throws Exception {
        List<Person> existingPersons = personDao.findAll();

        assertNotNull(existingPersons);

        this.mvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(existingPersons.size())))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void deletePerson() throws Exception {
        Person personToDelete = personDao.findByName("Test Name");

        assertNotNull(personToDelete);

        this.mvc.perform(delete("/persons/" + personToDelete.getId()))
                .andExpect(status().isNoContent());

        Person afterDeletion = personDao.findByName("Test Name");
        assertNull(afterDeletion);
    }

    @Test
    public void getPerson() throws Exception {
        Person personToFind = personDao.findByName("Test Name");

        assertNotNull(personToFind);

        this.mvc.perform(get("/persons/" + personToFind.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is((int) personToFind.getId())))
                .andExpect(jsonPath("$['name']", is(personToFind.getName())))
                .andExpect(jsonPath("$['age']", is(personToFind.getAge())))
                .andExpect(jsonPath("$['dateJoined']", is(personToFind.getDateJoined().toString())))
                .andExpect(jsonPath("$['dateUpdated']", is(personToFind.getDateUpdated().toString())));
    }

    @Test
    public void updatePerson() throws Exception {
        Person personToUpdate = personDao.findByName("Test Name");
        ObjectMapper obj = new ObjectMapper();

        assertNotNull(personToUpdate);

        personToUpdate.setName("Changed Name");
        personToUpdate.setAge(21);
        personToUpdate.setJob(jobDao.findByJobTitle("Temporary Test"));
        personToUpdate.setDateUpdated(Date.valueOf("2006-06-06"));
        personToUpdate.setDateJoined(Date.valueOf("2013-03-03"));

        String jsonStr = obj.writeValueAsString(personToUpdate);

        this.mvc.perform(put("/persons/" + personToUpdate.getId())
                .contentType("application/json")
                .content(jsonStr))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$['id']", is((int) personToUpdate.getId())))
                .andExpect(jsonPath("$['name']", is(personToUpdate.getName())))
                .andExpect(jsonPath("$['age']", is(personToUpdate.getAge())))
                .andExpect(jsonPath("$['job'].id", is((int) personToUpdate.getJob().getId())))
                .andExpect(jsonPath("$['dateJoined']", is(personToUpdate.getDateJoined().toString())))
                .andExpect(jsonPath("$['dateUpdated']", is(personToUpdate.getDateUpdated().toString())));


        Person afterUpdate = personDao.getOne(personToUpdate.getId());
        assertNotNull(afterUpdate);
        personDao.delete(afterUpdate);
    }

}