package com.librotech.catalog.controller;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks(){
        List<BookResponse> bookResponseList = bookServices.getAllBooks();
        ApiResponse<List<BookResponse>> response = ApiResponse.success(bookResponseList, "List of books obtained");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody @Valid BookRequest request){
        BookResponse bookCreated = bookServices.addBook(request);
        ApiResponse<BookResponse> response = ApiResponse.success(bookCreated, "Book created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/search-by-description")
    public ResponseEntity<ApiResponse<BookResponse>> getBookFromDescription(@RequestBody @Valid BookDescriptionRequest request) {
        BookResponse book = bookServices.findBookByDescription(request.getDescription());
        ApiResponse<BookResponse> response = ApiResponse.success(book, "Book found successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookServices.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
