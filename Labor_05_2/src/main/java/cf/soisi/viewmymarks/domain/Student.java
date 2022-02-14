package cf.soisi.viewmymarks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {

    @Length(min = 8, max = 8)
    @Id
    private String id;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;
}
