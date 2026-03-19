package school.hei.td2.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import school.hei.td2.model.Student;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public String getAllStudentsNames() {
        return students.stream()
                .map(student -> student.getFirstName() + " " + student.getLastName())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("No students found");
    }

    public List<Student> addStudents(List<Student> newStudents) {
        students.addAll(newStudents);
        return students;
    }
}