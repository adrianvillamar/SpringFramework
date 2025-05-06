package com.adrian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> findByProjectId(Long id); // Assuming you have a Project entity with a one-to-many relationship with Issue
    
}
