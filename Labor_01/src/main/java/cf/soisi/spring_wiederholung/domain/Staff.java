package cf.soisi.spring_wiederholung.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Data
public class Staff {

    @Id
    @Length(max = 6)
    @NotBlank
    private String id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private Collection<Task> tasks;
}
