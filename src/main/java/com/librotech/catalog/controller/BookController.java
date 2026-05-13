package com.librotech.catalog.controller;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.BookDescriptionRequest;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        List<BookResponse> bookResponseList = bookServices.getAllBooks();
        return ResponseEntity.ok(bookResponseList);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest request){
        BookResponse bookCreated = bookServices.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookCreated);
    }

    @PostMapping("/search-by-description")
    public ResponseEntity<BookResponse> getBookFromDescription(@RequestBody @Valid BookDescriptionRequest request) {
        BookResponse book = bookServices.findBookByDescription(request.getDescription());
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookServices.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequest bookRequest){
        bookServices.updateBook(id, bookRequest);
        return ResponseEntity.noContent().build();
    }
}
