package com.librotech.catalog.controller;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.BookDescriptionRequest;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.dto.BookResumeDTO;
import com.librotech.catalog.validation.OnCreate;
import com.librotech.catalog.validation.OnPatch;
import com.librotech.catalog.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    private final BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

//    @GetMapping
//    public ResponseEntity<Page<BookResponse>> getBooks(
//            @RequestParam(required = false) String author,
//            @PageableDefault(size = 10, sort = "title") Pageable pageable
//    ) {
//        Page<BookResponse> bookResponses;
//
//        if (author != null && !author.isBlank()) {
//            bookResponses = bookServices.getBookByAuthor(pageable, author);
//        } else {
//            bookResponses = bookServices.getAllBooks(pageable);
//        }
//
//        return ResponseEntity.ok(bookResponses);
//    }
    /**
     * GET /api/libros?page=0
     * Retorna un fragmento (Slice) del catálogo con metadatos de navegación.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCatalog(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Slice<BookResumeDTO> slice = bookServices.getCatalog(page, size);

        // Construimos una respuesta con metadatos de navegación
        Map<String, Object> response = new HashMap<>();
        response.put("books", slice.getContent());
        response.put("currentPage", slice.getNumber());
        response.put("pageSize", slice.getSize());
        response.put("hasNext", slice.hasNext());
        response.put("hasPrevious", slice.hasPrevious());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/country")
    public ResponseEntity<Slice<BookResumeDTO>> getBooksByCountry(
            @RequestParam String country, @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        Slice<BookResumeDTO> slice = bookServices.findByCountry(country, pageable);
        return ResponseEntity.ok(slice);
    }

    @GetMapping("/test")
    public ResponseEntity<List<BookResumeDTO>> getBooks() {
        return ResponseEntity.ok(bookServices.getBookWithRelationsResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResumeDTO> findById(@PathVariable Long id) {
        BookResumeDTO book = bookServices.getBookWithRelations(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Validated(OnCreate.class) BookRequest request) {
        BookResponse bookCreated = bookServices.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookCreated);
    }

    @PostMapping("/search-by-description")
    public ResponseEntity<List<BookResponse>> getBookFromDescription(@RequestBody @Valid BookDescriptionRequest request) {
        List<BookResponse> books = bookServices.findBooksByDescription(request.description());
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookServices.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) BookRequest bookRequest) {
        bookServices.updateBook(id, bookRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchBook(@PathVariable Long id, @RequestBody @Validated(OnPatch.class) BookRequest bookRequest) {
        bookServices.patchBook(id, bookRequest);
        return ResponseEntity.noContent().build();
    }
}
