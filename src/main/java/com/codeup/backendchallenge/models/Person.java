package com.codeup.backendchallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@RequiredArgsConstructor
@Table(name="persons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int age;

    @Column(name = "date_joined")
    private Date dateJoined;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @OneToOne
    private Job job;

    public Person(String name, int age, Date dateJoined, Date dateUpdated, Job job) {
        this.name = name;
        this.age = age;
        this.dateJoined = dateJoined;
        this.dateUpdated = dateUpdated;
        this.job = job;
    }
}
