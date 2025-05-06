package com.adrian.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.model.Comment;
import com.adrian.model.Issue;
import com.adrian.model.User;
import com.adrian.repository.CommentRepository;
import com.adrian.repository.IssueRepository;
import com.adrian.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository; 

    @Autowired
    private IssueRepository issueRepository; // Assuming you have an IssueRepository to fetch the issue

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository to fetch the user

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!issueOptional.isEmpty()) {
            throw new Exception("Issue not found with id" + issueId);
        }
        if (!userOptional.isEmpty()) {
            throw new Exception("User not found with id" + userId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();
        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment); // Assuming Issue has a List<Comment> comments field
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found with id" + commentId);
        }
        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id" + userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if (comment.getUser().equals(user)) {
            commentRepository.delete(comment);
        } else {        
            throw new Exception("User not authorized to delete this comment");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findCommentByIssueId(issueId);
    }   
    
}
