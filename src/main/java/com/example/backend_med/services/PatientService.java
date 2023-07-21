package com.example.backend_med.services;

import com.example.backend_med.models.Meet;
import com.example.backend_med.models.Patient;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public ResponseEntity<Object> findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Patient patient = mongoTemplate.findOne(query, Patient.class);
        return ResponseHandler.generateResponse("paciente actualizado", HttpStatus.OK, patient);
    }

    public ResponseEntity<Object> findByDocument(String document) {
        List<Patient> coincidences = mongoTemplate
                .find(new Query(Criteria.where("identificationCard").regex(document + ".*")), Patient.class);
        return ResponseHandler.generateResponse("coincidencias", HttpStatus.OK, coincidences);

    }

    public ResponseEntity<Object> update(Patient patient, String id) {
        patient.set_id(id);
        Patient updatedPatient = mongoTemplate.save(patient, "patients");
        return ResponseHandler.generateResponse("paciente actualizado", HttpStatus.OK, updatedPatient);

    }

    public ResponseEntity<Object> delete(String id) {
        Patient patient = mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(id)), Patient.class);
        mongoTemplate.findAllAndRemove(new Query(Criteria.where("patient._id").is(id)), Meet.class);
        return ResponseHandler.generateResponse("paciente y citas asociadas eliminados", HttpStatus.OK, patient);
    }

}
