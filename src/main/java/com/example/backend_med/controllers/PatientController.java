package com.example.backend_med.controllers;

import com.example.backend_med.models.Patient;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    private final MongoTemplate mongoTemplate;

    @PostMapping("/patients")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        Patient patient1 = mongoTemplate
                .save(patient, "patients");
        return ResponseEntity.status(200).body(patient1);
    }

    @GetMapping("/patients")
    public ResponseEntity<Object> findAll() {
        List<Patient> patients = mongoTemplate.findAll(Patient.class);
        return ResponseHandler.generateResponse("lista ok", HttpStatus.OK, patients);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Patient patient = mongoTemplate.findOne(query, Patient.class);
        return ResponseHandler.generateResponse("paciente actualizado", HttpStatus.OK, patient);
    }

    @GetMapping("/patients/search/{document}")
    public ResponseEntity<Object> findByDocument(@PathVariable String document) {
        List<Patient> coincidences = mongoTemplate
                .find(new Query(Criteria.where("identificationCard").regex(document + ".*")), Patient.class);
        return ResponseHandler.generateResponse("coincidencias", HttpStatus.OK, coincidences);
    }

    @PatchMapping("/patients/{id}")
    public ResponseEntity<Object> updateDoctor(@RequestBody Patient patient, @PathVariable String id) {
        patient.set_id(id);
        Patient updatedPatient = mongoTemplate.save(patient, "patients");
        return ResponseHandler.generateResponse("paciente actualizado", HttpStatus.OK, updatedPatient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable String id) {
        Patient deletedPatient = mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(id)), Patient.class);
        return ResponseHandler.generateResponse("coincidencias", HttpStatus.OK, deletedPatient);
    }


}
