package com.librotech.catalog.controller.ui;

import com.librotech.catalog.Service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/chat")
public class ChatUIController {

    private final MessageService messageService;

    public ChatUIController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String showChatRoom(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        model.addAttribute("screenTitle", "Chat Room");

        return "chat/chat-room";
    }
}
