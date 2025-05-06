package com.adrian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.model.Chat;
import com.adrian.model.Message;
import com.adrian.model.User;
import com.adrian.request.MessageRequest;
import com.adrian.services.MessageService;
import com.adrian.services.ProjectService;
import com.adrian.services.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
        @RequestBody MessageRequest request) 
        throws Exception {
        
        User user = userService.findUserById(request.getSenderId());
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats == null) {
            throw new Exception("Chats not found");
        }
        Message sentMessage = messageService.sendMessage(
            request.getSenderId(), 
            request.getProjectId(), 
            request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")    
    public ResponseEntity<List<Message>> getMessagesByChatId(
        @PathVariable Long projectId) 
        throws Exception {
            
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }    
}
