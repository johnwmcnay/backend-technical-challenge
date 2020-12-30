package com.codeup.backendchallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "job_title", unique = true)
    private String jobTitle;

    @Column
    private int salary;

//    Add another object, Job and give it the appropriate db relationship to Person, set
//    up automated tests for this object and it’s endpoints too
//○ id
//○ jobTitle
//○ Salary
//● Should be able to add a job, get a job, update a job, delete a job, get all jobs
//            (CRUD)
//● Should be able to update an existing Persons job
//● Write a small SQL seeder file and add some Persons and Jobs to the database
}
