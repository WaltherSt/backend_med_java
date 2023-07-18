package com.example.backend_med.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "doctors")
@Data
public class Doctor {
    @Id
    private String _id;
    private String name;
    private String lastName;
    @DocumentReference
    private Specialty specialty;
    private String consultingRoom;
    private String email;

    public Doctor() {
    }
    public Doctor(String id) {
        set_id(id);
    }


}
