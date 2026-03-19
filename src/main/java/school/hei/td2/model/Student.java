package school.hei.td2.model;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String reference;
    private String firstName;
    private String lastName;
    private int age;
}