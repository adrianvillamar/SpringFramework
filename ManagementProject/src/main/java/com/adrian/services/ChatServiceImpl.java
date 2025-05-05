package com.adrian.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.model.Chat;
import com.adrian.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository; // Repositorio para acceder a los datos de los chats

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat); // Retornar el chat creado (o null si no se implementa)
    }
    
}
