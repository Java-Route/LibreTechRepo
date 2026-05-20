package com.librotech.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "mensajes")
public class Message {
    @Id
    private String id;
    private String sender;
    private String content;
    private LocalDateTime shippingDate = LocalDateTime.now();

    // Getters, Setters y Constructores
}