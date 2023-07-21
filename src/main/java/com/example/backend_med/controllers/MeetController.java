package com.example.backend_med.controllers;

import com.example.backend_med.dtos.MeetDTO;
import com.example.backend_med.dtos.MeetDoctorDate;
import com.example.backend_med.models.Meet;
import com.example.backend_med.pipelines.MeetAggregation;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MeetController {

    private final MongoTemplate mongoTemplate;

    @PostMapping("/meets")
    public ResponseEntity<Object> create(@RequestBody Meet meet) {
        Meet meet1 = mongoTemplate.save(meet, "meets");
        return ResponseHandler.generateResponse("registrado", HttpStatus.OK, meet1);
    }

    @GetMapping("/meets")
    public ResponseEntity<Object> findAll() {
        Aggregation aggregation = MeetAggregation.aggregation();
        AggregationResults<MeetDTO> meets = mongoTemplate.aggregate(aggregation, "meets", MeetDTO.class);
        return ResponseHandler.generateResponse("lista de citas", HttpStatus.OK, meets.getMappedResults());
    }

    @GetMapping("/meets/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        Aggregation aggregation = MeetAggregation.aggregation(id);
        AggregationResults<MeetDTO> meets = mongoTemplate.aggregate(aggregation, "meets", MeetDTO.class);
        return ResponseHandler.generateResponse("cita", HttpStatus.OK, meets.getMappedResults());
    }

    @DeleteMapping("/meets/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Meet meet = mongoTemplate.findAndRemove(query, Meet.class);
        return ResponseHandler.generateResponse("cita eliminada", HttpStatus.OK, "");
    }

    @GetMapping("/meets/{id_doctor}/{date}")
    public ResponseEntity<Object> getMeetsByDoctorAndDate(@PathVariable String id_doctor, @PathVariable String date) {
        Aggregation meetAggregation = MeetAggregation
                .aggregation(id_doctor, date);
        AggregationResults<MeetDoctorDate> meetList = mongoTemplate
                .aggregate(meetAggregation, "meets", MeetDoctorDate.class);
        return ResponseHandler.generateResponse("lista de citas por horas ocupadas", HttpStatus.OK, meetList.getMappedResults());
    }
}
