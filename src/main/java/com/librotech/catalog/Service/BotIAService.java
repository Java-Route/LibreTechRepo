package com.librotech.catalog.Service;

import com.librotech.catalog.model.Message;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BotIAService {

    private final ChatAiProvider chatAiProvider;

    private final MessageService messageService;

    public BotIAService(ChatAiProvider chatAiProvider, MessageService messageService) {
        this.chatAiProvider = chatAiProvider;
        this.messageService = messageService;
    }

    public Message generateAIResponse(String userQuestion) {
        // 1. Extraer el contexto de la base de datos (Ej: Últimos 10 mensajes)
        String mongoHistory = messageService.getAllMessages()
                .stream()
                .map(m -> m.getSender() + ": " + m.getContent())
                .collect(Collectors.joining("\n"));

        // 2. Construir el Prompt combinando la base de datos y la pregunta
        String prompt = "Eres el asistente de LibroTech. Usa este historial de chat como contexto:\n"
                + mongoHistory + "\n\nResponde a: " + userQuestion;

        // 3. Llamar al proveedor de IA configurado
        String textResponse = chatAiProvider.generateResponse(prompt);

        // 4. Crear y guardar el mensaje del Bot
        Message mensajeBot = new Message();
        mensajeBot.setSender("LibroBot IA");
        mensajeBot.setContent(textResponse);
        return messageService.saveMessage(mensajeBot);
    }
}
