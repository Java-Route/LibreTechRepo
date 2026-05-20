package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.MessageRepository;
import com.librotech.catalog.model.Message;

public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message){
        messageRepository.insert(message);
    }
}
