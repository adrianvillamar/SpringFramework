package com.adrian.services;

import com.adrian.model.Invitation;

import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws MessagingException; // Sends an invitation email to a user for a specific project
    
    public Invitation acceptInvitation(String token, Long userId) throws Exception; // Accepts an invitation using a token and user ID

    public String getTokenByUserMail(String userEmail); // Retrieves the token associated with a user's email

    void deleteToken(String token); // Deletes the token associated with a user's email
}
