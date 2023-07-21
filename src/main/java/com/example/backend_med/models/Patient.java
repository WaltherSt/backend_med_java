package com.example.backend_med.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "patients")
@Data
public class Patient {
    @Id
    private String _id;
    private String name;
    private String lastName;
    private String identificationCard;
    private String age;
    private String phone;

    public Patient() {}

    public Patient(String id) {
        set_id(id);
    }


}
