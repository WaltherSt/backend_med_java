package com.example.backend_med.response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", status.value());
        res.put("message", message);
        res.put("data", responseObj);

        return new ResponseEntity<Object>(res, status);
    }
}
