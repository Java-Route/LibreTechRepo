package com.librotech.catalog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "editorials")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    // Nuevo campo: país de la editorial
    @Column(nullable = false)
    private String country;

    private Integer foundedIn;

    // Lado inverso de la relación: una Editorial tiene muchos Libros
    @OneToMany(mappedBy = "editorial")
    private List<Book> books = new ArrayList<>();

    // === Constructores ===
    public Editorial() {}

    public Editorial(Integer foundedIn, String country, String address, String name) {
        this.foundedIn = foundedIn;
        this.country = country;
        this.address = address;
        this.name = name;
    }
}