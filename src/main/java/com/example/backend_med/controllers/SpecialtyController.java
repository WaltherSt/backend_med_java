package com.example.backend_med.controllers;

import com.example.backend_med.interfaces.SpecialtyI;
import com.example.backend_med.models.Specialty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SpecialtyController {

    private final SpecialtyI specialtyService;

    @GetMapping("/specialties")
    public ResponseEntity<Object> findAll() {
        return specialtyService.findAll();
    }

    @PostMapping("/specialties")
    public ResponseEntity<Object> create(@RequestBody Specialty specialty) {
        return specialtyService.create(specialty);
    }
}
