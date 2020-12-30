package com.codeup.backendchallenge.repos;

import com.codeup.backendchallenge.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Job findByJobTitle(String title);
}
