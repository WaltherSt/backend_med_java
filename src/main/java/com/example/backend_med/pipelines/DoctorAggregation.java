package com.example.backend_med.pipelines;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
public class DoctorAggregation {
    public static Aggregation aggregation() {
        return newAggregation(
                lookup("specialties", "specialty", "_id", "specialtyData"),
                project("_id", "name", "lastName", "consultingRoom", "email")
                        .and("specialtyData.name").as("specialty")
                        .and("specialtyData._id").as("id_specialty")
        );
    }
    public static Aggregation aggregation(String id, Property property) {


        if (property == Property.SPECIALTY) {
            return newAggregation(
                    lookup("specialties", "specialty", "_id", "specialtyData"),
                    match(Criteria.where("specialty").is(id)),
                    project("_id", "name", "lastName", "consultingRoom", "email")
                            .and("specialtyData.name").as("specialty")
                            .and("specialtyData._id").as("id_specialty")
            );
        }
        return newAggregation(
                lookup("specialties", "specialty", "_id", "specialtyData"),
                match(Criteria.where("_id").is(id)),
                project("_id", "name", "lastName", "consultingRoom", "email")
                        .and("specialtyData.name").as("specialty")
                        .and("specialtyData._id").as("id_specialty")
        );
    }
}
