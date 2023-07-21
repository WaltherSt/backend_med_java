package com.example.backend_med.controllers;

import com.example.backend_med.models.Specialty;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SpecialtyController {

    private final MongoTemplate mongoTemplate;

    @GetMapping("/specialties")
    public ResponseEntity<Object> findAll() {
        List<Specialty> specialties = mongoTemplate
                .findAll(Specialty.class);
        return ResponseHandler.generateResponse("lista de citas", HttpStatus.OK, specialties);
    }

    @PostMapping("/specialties")
    public ResponseEntity<Object> create(@RequestBody Specialty specialty) {
        Specialty specialty1 = mongoTemplate.save(specialty, "specialties");
        return ResponseHandler.generateResponse("especialidad registrada", HttpStatus.CREATED, specialty1);
    }
}
