package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.EditorialRepository;
import com.librotech.catalog.model.Editorial;

import java.util.List;

public class EditorialServices {
    private final EditorialRepository editorialRepository;

    public EditorialServices(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public List<Editorial> getAllEditorials() {
        return editorialRepository.findAll();
    }
}
