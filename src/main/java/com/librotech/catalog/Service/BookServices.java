package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.BookRepository;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {
    private final BookRepository bookRepository;
    private final BookTitleAiService bookTitleAiService;

    public BookServices(BookRepository bookRepository, BookTitleAiService bookTitleAiService) {
        this.bookRepository = bookRepository;
        this.bookTitleAiService = bookTitleAiService;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getPublicationYear()
                )).toList();
    }

    public BookResponse addBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());

        Book savedBook = bookRepository.save(book);

        return new BookResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getIsbn(),
                savedBook.getPublicationYear()
        );
    }

    public BookResponse findBookByDescription(String description) {
        String title = bookTitleAiService.findTitleFromDescription(description);
        Book book = bookRepository.findFirstByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));

        return toBookResponse(book);
    }

    public void deleteBook(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    private BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear()
        );
    }
}
