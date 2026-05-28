package com.librotech.catalog.Service;

import com.librotech.catalog.Repository.EditorialRepository;
import com.librotech.catalog.dto.EditorialResponse;
import com.librotech.catalog.model.Editorial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialServices {
    private final EditorialRepository editorialRepository;

    public EditorialServices(EditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

//    public Slice<EditorialResponse> getAllEditorials(Pageable pageable) {
//        return editorialRepository.findAll(pageable);
//    }
}
