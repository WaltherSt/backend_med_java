package com.example.backend_med.services;

import com.example.backend_med.interfaces.SpecialtyI;
import com.example.backend_med.models.Specialty;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService implements SpecialtyI<Object> {

    private final MongoTemplate mongoTemplate;

    public ResponseEntity<Object> findAll() {
        List<Specialty> specialties = mongoTemplate
                .findAll(Specialty.class);
        return ResponseHandler.generateResponse("lista de citas", HttpStatus.OK, specialties);
    }

    public ResponseEntity<Object> create(Specialty specialty) {
        Specialty specialty1 = mongoTemplate.save(specialty, "specialties");
        return ResponseHandler.generateResponse("especialidad registrada", HttpStatus.CREATED, specialty1);
    }
}



