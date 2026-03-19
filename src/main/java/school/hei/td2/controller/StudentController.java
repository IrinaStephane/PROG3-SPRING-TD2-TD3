package school.hei.td2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import school.hei.td2.model.Student;
import school.hei.td2.service.StudentService;

@RestController
public class StudentController {

    private final StudentService studentService = new StudentService();

    // A) GET /welcome
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(value = "name", required = false) String name) {

        if (name == null || name.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Paramètre 'name' manquant");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body("Welcome " + name);
    }

    // B) POST /students
    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {

        try {
            List<Student> allStudents = studentService.addStudents(newStudents);

            return ResponseEntity
                    .status(HttpStatus.CREATED) // 201
                    .body(allStudents);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .build();
        }
    }

    // C) GET /students
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(@RequestHeader(value = "Accept", required = false) String accept) {

        try {
            // 1) Header absent → 400
            if (accept == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Header 'Accept' manquant");
            }

            // 2) text/plain → noms
            if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(studentService.getAllStudentsNames());
            }

            // 3) application/json → objets Student
            if (accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.getStudents());
            }

            // 4) format non supporté → 501
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body("Format non supporté");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur");
        }
    }
}