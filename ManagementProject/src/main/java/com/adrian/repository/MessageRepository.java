package com.adrian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId); 
}
