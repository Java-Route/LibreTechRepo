package com.librotech.catalog.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // Lado inverso de Many-to-Many
    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    // === Constructores ===


    public Genre() {
    }

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
