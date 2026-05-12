package com.librotech.catalog.Service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookTitleAiService {
    private final String apiKey;
    private final String modelName;

    public BookTitleAiService(
            @Value("${gemini.api-key:}") String apiKey,
            @Value("${gemini.model-name:gemini-2.5-flash}") String modelName
    ) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }

    public String findTitleFromDescription(String description) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Gemini API key is not configured");
        }

        ChatModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.0)
                .build();

        String prompt = """
                Eres un identificador de libros.
                A partir de la descripcion del usuario, responde UNICAMENTE con el titulo mas probable del libro.
                No agregues autor, explicaciones, comillas, puntos, formato Markdown ni texto adicional.
                Si no puedes identificar el libro, responde exactamente: Desconocido

                Descripcion:
                %s
                """.formatted(description);

        return cleanTitle(gemini.chat(prompt));
    }

    private String cleanTitle(String response) {
        if (response == null || response.isBlank()) {
            return "Desconocido";
        }

        String title = response.trim().lines().findFirst().orElse("Desconocido").trim();
        if ((title.startsWith("\"") && title.endsWith("\"")) || (title.startsWith("'") && title.endsWith("'"))) {
            title = title.substring(1, title.length() - 1).trim();
        }
        return title.isBlank() ? "Desconocido" : title;
    }
}
