package com.librotech.catalog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "books")
@SQLRestriction("available = true")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private LocalDate publicationDate;

    private Double price;

    // === SOFT DELETE ===
    @Column(nullable = false)
    private Boolean available = true;

    // === RELACIÓN MANY-TO-ONE: Cada libro pertenece a UNA editorial ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;

    // === RELACIÓN MANY-TO-MANY: Un libro tiene MUCHOS géneros ===
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_genres",                                // Nombre explícito de tabla intermedia
            joinColumns = @JoinColumn(name = "book_id"),           // FK hacia Libro
            inverseJoinColumns = @JoinColumn(name = "genre_id")    // FK hacia Genero
    )
    private Set<Genre> genres = new HashSet<>();

    // === Constructores ===

    public Book(String title, String author, String isbn, LocalDate publicationDate, Double price, Boolean available, Editorial editorial) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.price = price;
        this.available = true;
        this.editorial = editorial;
    }

    // === Método para "descatalogar" lógicamente ===
    public void softDelete() {
        this.available = false;
    }
}
