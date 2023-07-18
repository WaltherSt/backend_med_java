package com.example.backend_med.controllers;

import com.example.backend_med.dtos.DoctorDTO;
import com.example.backend_med.models.Doctor;
import com.example.backend_med.models.Meet;
import com.example.backend_med.pipelines.DoctorAggregation;
import com.example.backend_med.pipelines.Property;
import com.example.backend_med.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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
public class DoctorController {

    private final MongoTemplate mongoTemplate;

    @PostMapping("/doctors")
    public ResponseEntity<Object> create(@RequestBody Doctor doctor) {
        Doctor doc = mongoTemplate.save(doctor, "doctors");
        return ResponseHandler.generateResponse("registrado", HttpStatus.OK, doc);
    }

    @GetMapping("/doctors")
    public ResponseEntity<Object> findAll() {
        Aggregation aggregation = DoctorAggregation.aggregation();
        AggregationResults<DoctorDTO> doctors = mongoTemplate.aggregate(aggregation, "doctors", DoctorDTO.class);
        return ResponseHandler.generateResponse("lista de doctores", HttpStatus.OK, doctors.getMappedResults());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        Aggregation aggregation = DoctorAggregation.aggregation(id, Property.ID);
        AggregationResults<DoctorDTO> doctor = mongoTemplate.aggregate(aggregation, "doctors", DoctorDTO.class);
        return ResponseHandler.generateResponse("doctor", HttpStatus.OK, doctor.getMappedResults());
    }

    @PatchMapping("/doctors/{id}")
    public ResponseEntity<Object> update(@RequestBody Doctor doctor, @PathVariable String id) {
        doctor.set_id(id);
        Doctor doc = mongoTemplate.save(doctor, "doctors");
        return ResponseHandler.generateResponse("registrado", HttpStatus.OK, doc);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        Doctor doc = mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(id)), Doctor.class);
        mongoTemplate.findAllAndRemove(new Query(Criteria.where("doctor._id").is(id)), Meet.class);
        return ResponseHandler.generateResponse("doctor y citas asociadas eliminadas", HttpStatus.OK, doc);
    }

    @GetMapping("/doctors/specialty/{id}")
    public ResponseEntity<Object> doctorsBySpecialty(@PathVariable String id) {
        List<Doctor> doctorList = mongoTemplate.find(new Query(Criteria.where("specialty._id").is(id)), Doctor.class);
        return ResponseHandler.generateResponse("doctor", HttpStatus.OK, doctorList);
    }


}
