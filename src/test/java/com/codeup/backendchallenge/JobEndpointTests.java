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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendChallengeApplication.class)
@AutoConfigureMockMvc
public class JobEndpointTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    JobRepository jobDao;

    @Before
    public void setup() {

        Job job = jobDao.findByJobTitle("Test Job");

        if (job == null) {
            Job newJob = new Job(
                    "Test Job", 200000);
            jobDao.save(newJob);
        }
    }

    @After
    public void postTest() {
        Job job = jobDao.findByJobTitle("Test Job");

        if (job != null) {
            jobDao.delete(job);
        }
    }

    @Test
    public void contextLoads() {
        assertNotNull(mvc);
    }

    @Test
    public void testAddJob() throws Exception {

        Job job = new Job("Job to add", 123456);
        ObjectMapper obj = new ObjectMapper();
        String jsonStr = obj.writeValueAsString(job);

        this.mvc.perform(post("/job/add")
                .contentType("application/json")
                .content(jsonStr))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$['jobTitle']", is(job.getJobTitle())))
                .andExpect(jsonPath("$['salary']", is(job.getSalary())));

        jobDao.delete(jobDao.findByJobTitle(job.getJobTitle()));
    }

    @Test
    public void testAllJobs() throws Exception {
        List<Job> existingJobs = jobDao.findAll();

        assertNotNull(existingJobs);

        this.mvc.perform(get("/job/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(existingJobs.size())))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void deleteJob() throws Exception {
        Job jobToDelete = jobDao.findByJobTitle("Test Job");

        assertNotNull(jobToDelete);

        this.mvc.perform(post("/job/" + jobToDelete.getId() + "/delete"))
                .andExpect(status().isAccepted());

        Job afterDeletion = jobDao.findByJobTitle("Test Job");
        assertNull(afterDeletion);
    }

    @Test
    public void getJob() throws Exception {
        Job jobToFind = jobDao.findByJobTitle("Test Job");

        assertNotNull(jobToFind);

        this.mvc.perform(get("/job/" + jobToFind.getId() + "/get"))
                .andExpect(jsonPath("$['id']", is((int) jobToFind.getId())))
                .andExpect(jsonPath("$['jobTitle']", is(jobToFind.getJobTitle())))
                .andExpect(jsonPath("$['salary']", is(jobToFind.getSalary())));
    }

    @Test
    public void updateJob() throws Exception {
        Job jobToUpdate = jobDao.findByJobTitle("Test Job");
        ObjectMapper obj = new ObjectMapper();

        assertNotNull(jobToUpdate);

        jobToUpdate.setJobTitle("Changed Job");
        jobToUpdate.setSalary(2222222);

        String jsonStr = obj.writeValueAsString(jobToUpdate);

        this.mvc.perform(post("/job/" + jobToUpdate.getId() + "/update")
                .contentType("application/json")
                .content(jsonStr))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$['id']", is((int) jobToUpdate.getId())))
                .andExpect(jsonPath("$['jobTitle']", is(jobToUpdate.getJobTitle())))
                .andExpect(jsonPath("$['salary']", is(jobToUpdate.getSalary())));

        Job afterUpdate = jobDao.getOne(jobToUpdate.getId());
        assertNotNull(afterUpdate);
        jobDao.delete(afterUpdate);
    }

}