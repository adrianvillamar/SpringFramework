package com.adrian.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.model.Issue;
import com.adrian.model.Project;
import com.adrian.model.User;
import com.adrian.repository.IssueRepository;
import com.adrian.request.IssueRequest;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository; // Assuming you have an IssueRepository for database operations

    @Autowired
    private ProjectService projectService; // Assuming you have a ProjectService for project-related operations

    @Autowired
    private UserService userService; // Assuming you have a UserService for user-related operations

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isPresent()) {
            return issue.get(); // Return the issue if found
        } 
        throw new Exception("No Issues found with issueId: " + issueId);
        
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId); // Assuming you have a method in the repository to find issues by project ID
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectId());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setProject(project);

        return issueRepository.save(issue); // Save the issue to the database
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId); // Check if the issue exists
        issueRepository.deleteById(issueId); // Delete the issue from the database
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId); // Assuming you have a method to get a user by ID
        Issue issue = getIssueById(issueId); // Check if the issue exists
        issue.setAssignee(user); // Set the user as the assignee of the issue
        return issueRepository.save(issue); // Save the updated issue to the database
    }

    @Override
    public Issue updateIssue(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId); // Check if the issue exists
        issue.setStatus(status); // Update the status of the issue
        return issueRepository.save(issue); // Save the updated issue to the database
    }
    
}
