package com.adrian.services;

import java.util.List;

import com.adrian.model.Issue;
import com.adrian.model.User;
import com.adrian.request.IssueRequest;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateIssue(Long issueId, String status) throws Exception;
    
}
