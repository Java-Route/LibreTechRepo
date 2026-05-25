package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.BookRepository;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.model.Book;
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
        book.setPublicationDate(request.getPublicationDate());

        Book savedBook = bookRepository.save(book);

        return toBookResponse(savedBook);
    }

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
                            book.setTitle(bookRequest.getTitle());
                            book.setAuthor(bookRequest.getAuthor());
                            book.setIsbn(bookRequest.getIsbn());
                            book.setPublicationDate(bookRequest.getPublicationDate());
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
                    if (bookRequest.getPublicationDate() != null)
                        book.setPublicationDate(bookRequest.getPublicationDate());
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


    private BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationDate()
        );
    }
}
