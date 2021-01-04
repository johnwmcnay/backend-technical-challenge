package com.codeup.backendchallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
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

    public Job(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
    }
}
