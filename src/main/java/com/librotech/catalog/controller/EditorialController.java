package com.librotech.catalog.controller;

import com.librotech.catalog.Service.EditorialServices;
import com.librotech.catalog.dto.EditorialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EditorialController {
    private final EditorialServices editorialServices;

    public EditorialController(EditorialServices editorialServices) {
        this.editorialServices = editorialServices;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEditorials(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Slice<EditorialResponse> editorials = editorialServices.getAllEditorials(pageable);
        Map<String, Object> response = new HashMap<>();
        return ResponseEntity.ok(response);
    }
}
