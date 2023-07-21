package com.example.backend_med.services;

import com.example.backend_med.models.Patient;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final MongoTemplate mongoTemplate;

    public ResponseEntity<Object> createPatient(Patient data) {

        try {
            Patient patient = mongoTemplate.save(data, "patients");
            return ResponseHandler.generateResponse("paciente registrado", HttpStatus.CREATED, patient);
        } catch (MongoDataIntegrityViolationException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, e.getCause());
        }
    }

    public ResponseEntity<Object> findAllPatients() {
        List<Patient> patients = mongoTemplate.findAll(Patient.class);
        return ResponseHandler.generateResponse("lista de pacientes", HttpStatus.OK, patients);
    }

}
