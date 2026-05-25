package com.librotech.catalog.Service;

import com.librotech.catalog.model.Book;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookTitleAiService {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    private final String apiKey;
    private final String modelName;

    public BookTitleAiService(
            @Value("${gemini.api-key:}") String apiKey,
            @Value("${gemini.model-name:}") String modelName
    ) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }

    public List<Long> findBookIdsFromDescription(String description, List<Book> books) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Gemini API key is not configured");
        }
        if (books == null || books.isEmpty()) {
            return List.of();
        }

        ChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.0)
                .build();

        String prompt = """
                Eres un buscador de libros de un catalogo.
                Recibiras una descripcion del usuario y una lista de libros disponibles en la base de datos.
                Debes elegir solamente libros que esten dentro del catalogo.
                Responde UNICAMENTE con los IDs de los libros posibles, separados por comas.
                Ordena primero los resultados mas probables.
                Si no hay coincidencias razonables, responde exactamente: []

                Descripcion:
                %s

                Catalogo disponible:
                %s
                """.formatted(description, buildCatalog(books));

        return extractExistingIds(gemini.chat(prompt), books);
    }

    private String buildCatalog(List<Book> books) {
        StringBuilder catalog = new StringBuilder();
        for (Book book : books) {
            catalog.append("ID: ").append(book.getId())
                    .append(" | Titulo: ").append(book.getTitle())
                    .append(" | Autor: ").append(book.getAuthor())
                    .append(" | ISBN: ").append(book.getIsbn() == null ? "N/A" : book.getIsbn())
                    .append(" | Fecha de publicacion: ").append(book.getPublicationDate())
                    .append(System.lineSeparator());
        }
        return catalog.toString();
    }

    private List<Long> extractExistingIds(String response, List<Book> books) {
        if (response == null || response.isBlank()) {
            return List.of();
        }

        Set<Long> existingIds = new LinkedHashSet<>();
        for (Book book : books) {
            existingIds.add(book.getId());
        }

        List<Long> ids = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(response);
        while (matcher.find()) {
            Long id = Long.valueOf(matcher.group());
            if (existingIds.contains(id) && !ids.contains(id)) {
                ids.add(id);
            }
        }
        return ids;
    }
}
