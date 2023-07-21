package com.example.backend_med.services;

import com.example.backend_med.dtos.DoctorDTO;
import com.example.backend_med.models.Doctor;
import com.example.backend_med.pipelines.DoctorAggregation;
import com.example.backend_med.pipelines.Property;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final MongoTemplate mongoTemplate;


    public ResponseEntity<Object> create(Doctor doctor) {
        Doctor doc = mongoTemplate.save(doctor, "doctors");
        return ResponseHandler.generateResponse("registrado", HttpStatus.OK, doc);
    }

    public ResponseEntity<Object> findAll() {
        Aggregation aggregation = DoctorAggregation.aggregation();
        AggregationResults<DoctorDTO> doctors = mongoTemplate.aggregate(aggregation, "doctors", DoctorDTO.class);
        return ResponseHandler.generateResponse("lista de doctores", HttpStatus.OK, doctors.getMappedResults());
    }

    public ResponseEntity<Object> findById(String id) {
        Aggregation aggregation = DoctorAggregation.aggregation(id, Property.ID);
        AggregationResults<DoctorDTO> doctor = mongoTemplate.aggregate(aggregation, "doctors", DoctorDTO.class);
        return ResponseHandler.generateResponse("doctor", HttpStatus.OK, doctor.getMappedResults());

    }

    public ResponseEntity<Object> update(Doctor doctor, String id) {
        doctor.set_id(id);
        Doctor doc = mongoTemplate.save(doctor, "doctors");
        return ResponseHandler.generateResponse("registrado", HttpStatus.OK, doc);
    }


}
