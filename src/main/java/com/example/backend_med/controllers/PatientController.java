package com.example.backend_med.controllers;

import com.example.backend_med.models.Meet;
import com.example.backend_med.models.Patient;
import com.example.backend_med.response.ResponseHandler;
import com.example.backend_med.services.PatientService;
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
@CrossOrigin(origins = "*")
public class PatientController {

    private final MongoTemplate mongoTemplate;

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
    public ResponseEntity<Object> update(@RequestBody Patient patient, @PathVariable String id) {
        patient.set_id(id);
        Patient updatedPatient = mongoTemplate.save(patient, "patients");
        return ResponseHandler.generateResponse("paciente actualizado", HttpStatus.OK, updatedPatient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        Patient patient = mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(id)), Patient.class);
        mongoTemplate.findAllAndRemove(new Query(Criteria.where("patient._id").is(id)), Meet.class);
        return ResponseHandler.generateResponse("paciente y citas asociadas eliminados", HttpStatus.OK, patient);
    }


}
