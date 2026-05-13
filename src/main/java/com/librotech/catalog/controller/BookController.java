package com.librotech.catalog.controller;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.BookDescriptionRequest;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.validation.OnCreate;
import com.librotech.catalog.validation.OnPatch;
import com.librotech.catalog.validation.OnUpdate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    private final BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(@PageableDefault(size = 10, sort = "title") Pageable pageable){
        Page<BookResponse> bookResponseList = bookServices.getAllBooks(pageable);
        return ResponseEntity.ok(bookResponseList);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Validated(OnCreate.class) BookRequest request){
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
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) BookRequest bookRequest){
        bookServices.updateBook(id, bookRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchBook(@PathVariable Long id, @RequestBody @Validated(OnPatch.class) BookRequest bookRequest){
        bookServices.patchBook(id, bookRequest);
        return ResponseEntity.noContent().build();
    }
}
