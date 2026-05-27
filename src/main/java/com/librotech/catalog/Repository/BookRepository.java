package com.librotech.catalog.Repository;

import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.dto.BookResumeDTO;
import com.librotech.catalog.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthorIgnoreCase(Pageable pageable, String author);

    Optional<Book> findFirstByTitleIgnoreCase(String title);

    @Query("""
            SELECT new com.librotech.catalog.dto.BookResumeDTO(
                l.id,
                l.title,
                l.publicationDate,
                l.price,
                l.editorial.name,
                l.editorial.country
            )
            FROM Book l
            JOIN l.editorial
            ORDER BY l.publicationDate DESC
            """)
    Slice<BookResumeDTO> findAllResumeBooks(Pageable pageable);

    // ... dentro de la interfaz LibroRepository:

    /**
     * Carga un libro con sus relaciones (Editorial + Géneros) en UNA sola query.
     * Sin @EntityGraph, acceder a libro.getEditorial() o libro.getGeneros()
     * dispararía consultas adicionales (N+1).
     */
    @Override
    @EntityGraph(attributePaths = {"editorial", "genres"})
    Optional<Book> findById(Long id);

    /**
     * Lista completa con carga ansiosa de relaciones.
     * Útil para el panel de administración donde se necesitan todos los datos.
     */
    @EntityGraph(attributePaths = {"editorial", "genres"})
    @Query("SELECT l FROM Book l ORDER BY l.publicationDate DESC")
    List<Book> findAllWithRelations();
}
