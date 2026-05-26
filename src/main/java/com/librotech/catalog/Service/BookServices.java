package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.BookRepository;
import com.librotech.catalog.Repository.EditorialRepository;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.dto.BookResumeDTO;
import com.librotech.catalog.model.Book;
import com.librotech.catalog.model.Editorial;
import com.librotech.catalog.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServices {
    private final BookRepository bookRepository;
    private final EditorialRepository editorialRepository;
    private final BookTitleAiService bookTitleAiService;

    public BookServices(BookRepository bookRepository, EditorialRepository editorialRepository, BookTitleAiService bookTitleAiService) {
        this.bookRepository = bookRepository;
        this.editorialRepository = editorialRepository;
        this.bookTitleAiService = bookTitleAiService;
    }

    //@Transactional(readOnly = true)
    //Optimización de Rendimiento (Desactivación del Dirty Checking)(No lo hace): Hibernate realiza un seguimiento de todos los objetos que consultas para ver si sufrieron algún cambio antes de guardar
    //Optimización a Nivel de Base de Datos:
    //Prevención de Errores (Seguridad):
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toBookResponse);
    }

    //@Transactional(readOnly = true)
    public Page<BookResponse> getBookByAuthor(Pageable pageable, String author) {
        return bookRepository.findByAuthorIgnoreCase(pageable, author)
                .map(this::toBookResponse);
    }

    //@Transactional(readOnly = true)
    public List<Editorial> getAllEditorials() {
        return editorialRepository.findAll();
    }

    public BookResponse addBook(BookRequest request) {
        Editorial editorial = editorialRepository.findById(request.editorialId())
                .orElseThrow(() -> new RuntimeException("Editorial not found"));

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setPublicationDate(request.publicationDate());
        book.setEditorial(editorial);

        Book savedBook = bookRepository.save(book);

        return toBookResponse(savedBook);
    }

    //@Transactional(readOnly = true)
    public List<BookResponse> findBooksByDescription(String description) {
        List<Book> books = bookRepository.findAll();
        List<Long> matchingIds = bookTitleAiService.findBookIdsFromDescription(description, books);

        return matchingIds.stream()
                .map(id -> books.stream()
                        .filter(book -> book.getId().equals(id))
                        .findFirst()
                        .orElse(null))
                .filter(book -> book != null)
                .map(this::toBookResponse)
                .toList();
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    public void updateBook(Long id, BookRequest bookRequest) {
        bookRepository.findById(id)
                .map(book -> {
                            Editorial editorial = editorialRepository.findById(bookRequest.editorialId())
                                    .orElseThrow(() -> new RuntimeException("Editorial not found"));
                            book.setTitle(bookRequest.title());
                            book.setAuthor(bookRequest.author());
                            book.setIsbn(bookRequest.isbn());
                            book.setPublicationDate(bookRequest.publicationDate());
                            book.setEditorial(editorial);
                            return bookRepository.save(book);
                        }
                )
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void patchBook(Long id, BookRequest bookRequest) {
        bookRepository.findById(id)
                .map(book -> {
                    if (bookRequest.title() != null) book.setTitle(bookRequest.title());
                    if (bookRequest.author() != null) book.setAuthor(bookRequest.author());
                    if (bookRequest.isbn() != null) book.setIsbn(bookRequest.isbn());
                    if (bookRequest.publicationDate() != null)
                        book.setPublicationDate(bookRequest.publicationDate());
                    if (bookRequest.editorialId() != null) {
                        Editorial editorial = editorialRepository.findById(bookRequest.editorialId())
                                .orElseThrow(() -> new RuntimeException("Editorial not found"));
                        book.setEditorial(editorial);
                    }
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }


    public void discontinueBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        // En lugar de: libroRepository.delete(libro);
        // Hacemos Soft Delete:
        book.softDelete();
        bookRepository.save(book);
    }


    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Obtiene un "slice" (fragmento) del catálogo de libros.
     * No ejecuta COUNT → más rápido que Page para catálogos masivos.
     *
     * @param page número de página (0-indexed)
     * @return Slice con los DTOs de resumen y metadatos de navegación
     */
    public Slice<BookResumeDTO> getCatalog(int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return bookRepository.findAllResumeBooks(pageable);
    }

    /**
     * Obtiene un libro con TODAS sus relaciones cargadas (para edición/detalle).
     */
    public Book getBookWithRelations(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + id));
    }


    private BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getEditorial().getName(),
                book.getPrice(),
                book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
        );
    }
}
