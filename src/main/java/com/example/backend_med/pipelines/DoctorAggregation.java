package com.example.backend_med.pipelines;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class DoctorAggregation {
    public static Aggregation aggregation() {
        LookupOperation specialtyLookup = lookup("specialties", "specialty", "_id", "specialtyData");
        UnwindOperation specialtyUnwind = unwind("specialtyData");

        return newAggregation(
                specialtyLookup,
                specialtyUnwind,
                project("_id", "name", "lastName", "consultingRoom", "email")
                        .and("specialtyData.name").as("specialty")
                        .and("specialtyData._id").as("id_specialty")
        );
    }
    public static Aggregation aggregation(String id, Property property) {
        LookupOperation specialtyLookup = lookup("specialties", "specialty", "_id", "specialtyData");
        UnwindOperation specialtyUnwind = unwind("specialtyData");
        MatchOperation matchSpecialty = match(Criteria.where("specialty").is(id));

        if (property == Property.SPECIALTY) {
            return newAggregation(
                    specialtyLookup,
                    specialtyUnwind,
                    matchSpecialty,
                    project("_id", "name", "lastName", "consultingRoom", "email")
                            .and("specialtyData.name").as("specialty")
                            .and("specialtyData._id").as("id_specialty")
            );
        }
        return newAggregation(
                specialtyLookup,
                specialtyUnwind,
                match(Criteria.where("_id").is(id)),
                project("_id", "name", "lastName", "consultingRoom", "email")
                        .and("specialtyData.name").as("specialty")
                        .and("specialtyData._id").as("id_specialty")
        );
    }
}
