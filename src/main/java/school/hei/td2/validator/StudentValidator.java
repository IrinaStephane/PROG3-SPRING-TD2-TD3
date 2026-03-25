package school.hei.td2.validator;

import org.springframework.stereotype.Component;
import school.hei.td2.exception.BadRequestException;
import school.hei.td2.model.Student;

import java.util.List;

@Component
public class StudentValidator {

    public void validate(List<Student> students) {
        for (Student student : students) {
            if (student.getReference() == null || student.getReference().isBlank()) {
                throw new BadRequestException("Student.reference cannot be null or blank");
            }
            if (student.getFirstName() == null || student.getFirstName().isBlank()) {
                throw new BadRequestException("Student.firstName cannot be null or blank");
            }
            if (student.getLastName() == null || student.getLastName().isBlank()) {
                throw new BadRequestException("Student.lastName cannot be null or blank");
            }
        }
    }
}