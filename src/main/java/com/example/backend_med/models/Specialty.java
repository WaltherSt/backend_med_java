package com.example.backend_med.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "specialties")
@Data
public class Specialty {
    @Id
    public String _id;
    public String name;

    public Specialty() {

    }
    public Specialty(String id) {
        set_id(id);
    }


}
