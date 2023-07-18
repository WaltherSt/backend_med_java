package com.example.backend_med.pipelines;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class MeetAggregation {
    private MeetAggregation() {
    }


    public static Aggregation aggregation() {
        return newAggregation(
                lookup("doctors", "doctor", "_id", "doctorData"),
                lookup("patients", "patient", "_id", "patientData"),
                lookup("specialties", "specialty", "_id", "specialtyData"),
                project("date", "hour")
                        .and("patientData.name").as("patient")
                        .and("patientData.identificationCard").as("patientIdentification")
                        .and("patientData._id").as("id_patient")
                        .and("doctorData.name").as("doctor")
                        .and("doctorData._id").as("id_doctor")
                        .and("specialtyData.name").as("specialty")
        );
    }

    public static Aggregation aggregation(String id) {
        return newAggregation(
                lookup("doctors", "doctor", "_id", "doctorData"),
                lookup("patients", "patient", "_id", "patientData"),
                lookup("specialties", "specialty", "_id", "specialtyData"),
                match(Criteria.where("_id").is(id)),
                project("date", "hour")
                        .and("patientData.name").as("patient")
                        .and("patientData.identificationCard").as("patientIdentification")
                        .and("patientData._id").as("id_patient")
                        .and("doctorData.name").as("doctor")
                        .and("doctorData._id").as("id_doctor")
                        .and("specialtyData.name").as("specialty")
        );
    }

    public static Aggregation aggregation(String id_doctor, String date) {
        return newAggregation(
                match(Criteria.where("date")
                        .is(date)
                        .and("doctor")
                        .is(new ObjectId(id_doctor))),
                project("date", "hour", "_id")
        );
    }
}
