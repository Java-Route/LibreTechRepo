package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.BookRepository;
import com.librotech.catalog.model.Book;
import com.librotech.catalog.model.Message;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotIAService {

    private final ChatClient chatClient;

    private final MessageService messageService;

    private final BookRepository bookRepository;

    public BotIAService(ChatClient.Builder chatClientBuilder, MessageService messageService, BookRepository bookRepository) {
        this.chatClient = chatClientBuilder.build();
        this.messageService = messageService;
        this.bookRepository = bookRepository;
    }

    public Message generateAIResponse(String userQuestion) {
        // 1. Extraer el contexto de la base de datos (Ej: Últimos 10 mensajes)
        String mongoHistory = messageService.getAllMessages()
                .stream()
                .map(m -> m.getSender() + ": " + m.getContent())
                .collect(Collectors.joining("\n"));

        String bookCatalog = buildBookCatalog(bookRepository.findAll());

        // 2. Construir el Prompt combinando la base de datos y la pregunta
        String prompt = """
                Eres el asistente de LibroTech.

                Reglas:
                - Si el usuario pregunta por libros, autores, ISBN, anios de publicacion, recomendaciones o disponibilidad, usa UNICAMENTE el catalogo real de la base de datos.
                - No inventes libros, autores, ISBN ni anios que no aparezcan en el catalogo.
                - Si no hay datos suficientes en el catalogo, dilo claramente.
                - Para preguntas generales que no sean sobre libros, puedes usar el historial de chat como contexto.

                Catalogo real de libros en la base de datos:
                %s

                Historial de chat:
                %s

                Pregunta del usuario:
                %s
                """.formatted(bookCatalog, mongoHistory.isBlank() ? "Sin historial." : mongoHistory, userQuestion);

        // 3. Llamar al proveedor de IA configurado
        String textResponse = chatClient.prompt().user(prompt).call().content();

        // 4. Crear y guardar el mensaje del Bot
        Message mensajeBot = new Message();
        mensajeBot.setSender("LibroBot IA");
        mensajeBot.setContent(textResponse);
        return messageService.saveMessage(mensajeBot);
    }

    private String buildBookCatalog(List<Book> books) {
        if (books.isEmpty()) {
            return "No hay libros registrados en la base de datos.";
        }

        return books.stream()
                .map(book -> "ID: " + book.getId()
                        + " | Titulo: " + book.getTitle()
                        + " | Autor: " + book.getAuthor()
                        + " | ISBN: " + (book.getIsbn() == null || book.getIsbn().isBlank() ? "N/A" : book.getIsbn())
                        + " | Fecha de publicacion: " + book.getPublicationDate())
                .collect(Collectors.joining("\n"));
    }
}
