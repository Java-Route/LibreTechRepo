package com.librotech.catalog.Service;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BotIAService {

    private final ChatClient chatClient;

    private final MessageService messageService;

    public BotIAService(ChatClient.Builder chatClientBuilder, MessageService messageService) {
        this.chatClient = chatClientBuilder.build();
        this.messageService = messageService;
    }

    public Mensaje generarRespuestaIA(String preguntaUsuario) {
        // 1. Extraer el contexto de la base de datos (Ej: Últimos 10 mensajes)
        String historialMongo = mensajeService.obtenerHistorial()
                .stream()
                .map(m -> m.getRemitente() + ": " + m.getContenido())
                .collect(Collectors.joining("\n"));

        // 2. Construir el Prompt combinando la base de datos y la pregunta
        String prompt = "Eres el asistente de LibroTech. Usa este historial de chat como contexto:\n"
                + historialMongo + "\n\nResponde a: " + preguntaUsuario;

        // 3. Llamar a Spring AI
        String respuestaTexto = chatClient.prompt().user(prompt).call().content();

        // 4. Crear y guardar el mensaje del Bot
        Mensaje mensajeBot = new Mensaje();
        mensajeBot.setRemitente("LibroBot IA");
        mensajeBot.setContenido(respuestaTexto);
        return mensajeService.guardarMensaje(mensajeBot);
    }
}