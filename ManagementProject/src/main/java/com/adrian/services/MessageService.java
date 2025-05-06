package com.adrian.services;

import java.util.List;

import com.adrian.model.Message;

public interface MessageService {

    Message sendMessage(Long senderId, Long chatId, String content) throws Exception;   

    List<Message> getMessagesByProjectId(Long projectId) throws Exception;
    
}
