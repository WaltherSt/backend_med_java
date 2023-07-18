package com.example.backend_med.dtos;

import lombok.Data;

@Data
public class DoctorDTO {
    String _id;
    String name;
    String lastName;
    String consultingRoom;
    String email;
    String specialty;
    String id_specialty;


}