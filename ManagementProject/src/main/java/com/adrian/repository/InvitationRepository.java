package com.adrian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.model.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    
    Invitation findByToken(String token);
    Invitation findByEmail(String userEmail);
    
}
