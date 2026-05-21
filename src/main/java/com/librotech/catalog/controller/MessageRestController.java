package com.librotech.catalog.controller;

import com.librotech.catalog.Service.MessageService;
import com.librotech.catalog.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MessageRestController {

    private final MessageService messageService;

    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessageHistory() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
