package com.adrian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    // Este repositorio permite realizar operaciones CRUD sobre la entidad Chat.
    // No es necesario implementar métodos adicionales, ya que JpaRepository proporciona
    // métodos básicos como save(), findById(), deleteById(), etc.

    
    
}
