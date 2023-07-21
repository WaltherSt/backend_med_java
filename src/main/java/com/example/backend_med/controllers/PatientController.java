package com.example.backend_med.controllers;

import com.example.backend_med.models.Patient;
import com.example.backend_med.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/patients")
    public ResponseEntity<Object> create(@RequestBody Patient data) {
        return patientService.createPatient(data);
    }

    @GetMapping("/patients")
    public ResponseEntity<Object> findAll() {
        return patientService.findAllPatients();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        return patientService.findById(id);
    }

    @GetMapping("/patients/search/{document}")
    public ResponseEntity<Object> findByDocument(@PathVariable String document) {
        return patientService.findByDocument(document);
    }

    @PatchMapping("/patients/{id}")
    public ResponseEntity<Object> update(@RequestBody Patient patient, @PathVariable String id) {
        return patientService.update(patient, id);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return patientService.delete(id);
    }


}
