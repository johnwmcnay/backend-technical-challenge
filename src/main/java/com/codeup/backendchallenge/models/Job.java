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

    public Job(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
    }
}
