package com.librotech.catalog.controller.ui;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.BookResponse;
import com.librotech.catalog.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class LibroUIController {


    private final BookServices bookServices;

    public LibroUIController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping
    public String listarLibrosUI(Model model, @PageableDefault(size = 4, sort = "id") Pageable pageable) {
        Page<BookResponse> books = bookServices.getAllBooks(pageable);

        // Pasamos la lista de libros a la vista con el nombre "libros"
        model.addAttribute("books", books);
        model.addAttribute("screenTitle", "Catálogo de Libros - Dashboard");

        // Retornamos el nombre del archivo HTML (sin la extensión .html)
        return "books/list";
    }
}