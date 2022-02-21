package cf.soisi.personinterest.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    public static final String NAME_REGEX = "^[A-Z]\\p{L}{0,29}$";
    @Id
    @GeneratedValue
    private Integer id;

    //firstName must start with an uppercase letter and is followed by max 29 unicode letters
    @Pattern(regexp = NAME_REGEX)
    private String firstName;

    @Pattern(regexp = NAME_REGEX)
    private String lastName;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max = 3)
    private Set<Interest> interests = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Sex sex;
}
