package com.librotech.catalog.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiChatProvider implements ChatAiProvider {

    private final ChatClient chatClient;

    public SpringAiChatProvider(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generateResponse(String prompt) {
        return chatClient.prompt().user(prompt).call().content();
    }
}
