package cf.soisi.personinterest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

    public Person(String firstName, String lastName, LocalDate dateOfBirth, Sex sex, Interest... interests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.interests = Set.of(interests);
        this.sex = sex;
    }

    public static final String NAME_REGEX = "^[A-Z]\\p{L}{0,29}$";
    @Id
    @GeneratedValue
    private Integer id;

    @Pattern(regexp = NAME_REGEX, message = "First name must start with an uppercase letter and is followed by max 29 unicode letters")
    private String firstName;

    @Pattern(regexp = NAME_REGEX,message = "Last name must start with an uppercase letter and is followed by max 29 unicode letters")
    private String lastName;

    @PastOrPresent(message = "Date of birth must be in the past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth must be specified")
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max = 3, message = "You can choose at most 3 interests")
    private Set<Interest> interests = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Sex sex;

    public String getFormattedInterests() {
        return this.interests
                .stream()
                .map(Interest::getDescription)
                .collect(Collectors.joining(", "));
    }
}
