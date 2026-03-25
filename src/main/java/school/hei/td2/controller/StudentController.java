package school.hei.td2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import school.hei.td2.exception.BadRequestException;
import school.hei.td2.model.Student;
import school.hei.td2.service.StudentService;
import school.hei.td2.validator.StudentValidator;

@RestController
public class StudentController {

    private final StudentService studentService = new StudentService();
    private final StudentValidator studentValidator = new StudentValidator();

    // POST /students
    @PostMapping("/students")
    public ResponseEntity<?> addStudents(@RequestBody List<Student> newStudents) {

        try {
            // validation déplacée dans Validator
            studentValidator.validate(newStudents);

            List<Student> allStudents = studentService.addStudents(newStudents);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(allStudents);

        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // GET /students
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(@RequestHeader(value = "Accept", required = false) String accept) {

        if (accept == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Header 'Accept' manquant");
        }

        if (accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(studentService.getAllStudentsNames());
        }

        if (accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(studentService.getStudents());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body("Format non supporté");
    }
}