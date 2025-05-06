package com.adrian.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate; 
    private Long projectId;
    private List<String> tags = new ArrayList<>(); 
    private Project project;
    private User assignee;
    



    
}
