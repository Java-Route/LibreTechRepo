package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.BookRepository;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.model.Book;
import dev.langchain4j.agent.tool.P;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toBookResponse);
    }

    public Page<BookResponse> getBookByAuthor(Pageable pageable, String author) {
        return bookRepository.findByAuthorIgnoreCase(pageable, author)
                .map(this::toBookResponse);
    }

    public BookResponse addBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());

        Book savedBook = bookRepository.save(book);

        return toBookResponse(savedBook);
    }

    public BookResponse findBookByDescription(String description) {
        String title = bookTitleAiService.findTitleFromDescription(description);
        Book book = bookRepository.findFirstByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));

        return toBookResponse(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    public void updateBook(Long id, BookRequest bookRequest) {
        bookRepository.findById(id)
                .map(book -> {
                            book.setTitle(bookRequest.getTitle());
                            book.setAuthor(bookRequest.getAuthor());
                            book.setIsbn(bookRequest.getIsbn());
                            book.setPublicationYear(bookRequest.getPublicationYear());
                            return bookRepository.save(book);
                        }
                )
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void patchBook(Long id, BookRequest bookRequest) {
        bookRepository.findById(id)
                .map(book -> {
                    if (bookRequest.getTitle() != null) book.setTitle(bookRequest.getTitle());
                    if (bookRequest.getAuthor() != null) book.setAuthor(bookRequest.getAuthor());
                    if (bookRequest.getIsbn() != null) book.setIsbn(bookRequest.getIsbn());
                    if (bookRequest.getPublicationYear() != null)
                        book.setPublicationYear(bookRequest.getPublicationYear());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
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
