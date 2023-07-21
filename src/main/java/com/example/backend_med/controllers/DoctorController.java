package com.example.backend_med.controllers;

import com.example.backend_med.models.Doctor;
import com.example.backend_med.models.Meet;
import com.example.backend_med.response.ResponseHandler;
import com.example.backend_med.services.DoctorService;
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
public class DoctorController {

    private final MongoTemplate mongoTemplate;
    private final DoctorService doctorService;

    @PostMapping("/doctors")
    public ResponseEntity<Object> create(@RequestBody Doctor doctor) {
        return doctorService.create(doctor);
    }

    @GetMapping("/doctors")
    public ResponseEntity<Object> findAll() {
        return doctorService.findAll();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        return doctorService.findById(id);
    }

    @PatchMapping("/doctors/{id}")
    public ResponseEntity<Object> update(@RequestBody Doctor doctor, @PathVariable String id) {
        return doctorService.update(doctor, id);
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
