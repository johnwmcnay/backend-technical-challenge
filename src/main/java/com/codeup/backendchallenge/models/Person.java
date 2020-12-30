package com.codeup.backendchallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
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

    public Person(String name, int age, Date dateJoined, Date dateUpdated) {
        this.name = name;
        this.age = age;
        this.dateJoined = dateJoined;
        this.dateUpdated = dateUpdated;
    }
}
