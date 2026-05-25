package com.librotech.catalog.controller.ui;

import com.librotech.catalog.Service.BookServices;
import com.librotech.catalog.dto.BookRequest;
import com.librotech.catalog.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

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

    // ... código anterior ...

    @GetMapping("/new")
    public String mostrarFormularioCreacion(Model model) {
        // Pasamos una instancia vacía que el formulario llenará
        model.addAttribute("book", new BookRequest());
        model.addAttribute("screenTitle", "Registrar Nuevo Libro");

        return "books/form";
    }

    @PostMapping("/save")
    public String guardarLibro(@ModelAttribute("book") BookRequest bookRequest, Model model) {

        LocalDate today = LocalDate.now();
        if (bookRequest.getPublicationDate() != null && bookRequest.getPublicationDate().isAfter(today)) {
            model.addAttribute("yearError", "La fecha de publicación no puede ser mayor a la fecha actual (" + today + ")");
            model.addAttribute("screenTitle", "Registrar Nuevo Libro (Corrección)");

            // Retornamos la vista del formulario (NO usamos redirect, para mantener los datos tipeados)
            return "books/form";
        }
        // Guardamos el libro usando el servicio
        bookServices.addBook(bookRequest);

        // Patrón PRG: Redirigimos a la lista de libros (GET)
        // La palabra clave "redirect:" le dice a Spring que emita un HTTP 302
        return "redirect:/admin/books";
    }
}
