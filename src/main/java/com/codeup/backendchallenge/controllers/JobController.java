package com.codeup.backendchallenge.controllers;


import com.codeup.backendchallenge.models.Job;
import com.codeup.backendchallenge.repos.JobRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/jobs")
public class JobController {
    private final JobRepository jobDao;

    //create job
    @PostMapping( consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Job addJob(@RequestBody Job job) {
        Preconditions.checkNotNull(job);
        return jobDao.save(job);
    }

    //return single job
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Job getJob(@PathVariable(name = "id") long id) {
        return jobDao.getOne(id);
    }

    //return all jobs
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Job> getAllJobs() {
        return jobDao.findAll();
    }

    //update job
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Job updateJob(@PathVariable(name = "id") long id, @RequestBody Job job) {
        Preconditions.checkNotNull(job);
        Job jobToUpdate = jobDao.getOne(id);

        jobToUpdate.setJobTitle(job.getJobTitle() == null ? jobToUpdate.getJobTitle() : job.getJobTitle());
        jobToUpdate.setSalary(job.getSalary() == 0 ? jobToUpdate.getSalary() : job.getSalary());

        return jobDao.save(jobToUpdate);
    }

    //delete job
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable(name = "id") long id) {
        jobDao.deleteById(id);
    }

}
