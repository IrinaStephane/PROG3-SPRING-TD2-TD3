package school.hei.td2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import school.hei.td2.model.Student;
import school.hei.td2.service.StudentService;

@RestController
public class StudentController {

    private final StudentService studentService = new StudentService();

    @GetMapping("/welcome")
    public String welcome(@RequestParam("name") String name) {
        return "Welcome "+ name;
    }

    @GetMapping("/students")
    public String getAllStudents(@RequestHeader("Accept") String accept) {
        if (accept.equals("text/plain")) {
            return studentService.getAllStudentsNames();
        }
        throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Format non supporté");
    }

    @PostMapping("/students")
    public List<Student> addStudents(@RequestBody List<Student> newStudents) {
        return studentService.addStudents(newStudents);
    }
}