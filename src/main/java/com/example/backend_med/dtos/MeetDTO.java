package com.example.backend_med.dtos;

import lombok.Data;

@Data
public class MeetDTO {
    private String date;
    private String hour;
    private String patient;
    private String patientIdentification;
    private String id_patient;
    private String doctor;
    private String id_doctor;
    private String specialty;


}
