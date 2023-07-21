package com.example.backend_med.interfaces;

import com.example.backend_med.models.Specialty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface SpecialtyI<T> {

    ResponseEntity<T> findAll();

    ResponseEntity<T> create(@RequestBody Specialty specialty);
}
