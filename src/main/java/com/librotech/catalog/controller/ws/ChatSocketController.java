package com.librotech.catalog.controller.ws;

import com.librotech.catalog.Service.MessageService;
import com.librotech.catalog.Service.BotIAService;
import com.librotech.catalog.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    private final MessageService messageService;

    private final BotIAService botIAService;

    private final SimpMessagingTemplate messagingTemplate;

    public ChatSocketController(MessageService messageService, BotIAService botIAService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.botIAService = botIAService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/enviar") // Ruta: /app/enviar
    @SendTo("/tema/mensajes") // Difunde el mensaje del usuario a todos
    public Message processUserMessage(Message receivedMessage) {

        // 1. Guardar en MongoDB el mensaje del usuario
        Message savedMessage = messageService.saveMessage(receivedMessage);

        // 2. Generar respuesta de la IA en un hilo separado (para no bloquear)
        new Thread(() -> {
            Message respuestaIA = botIAService.generateAIResponse(savedMessage.getContent());
            // 3. Enviar la respuesta de la IA al canal de WebSockets
            messagingTemplate.convertAndSend("/tema/mensajes", respuestaIA);
        }).start();

        return savedMessage;
    }
}
