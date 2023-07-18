package com.example.backend_med.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "meets")
@Data
public class Meet {
    @Id
    private String id;
    private String date;
    private String hour;
    @DocumentReference
    private Specialty specialty;
    @DocumentReference
    private Doctor doctor;
    @DocumentReference
    private Patient patient;
}
