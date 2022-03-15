package cf.soisi.springsecurity.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Frage {
    @GeneratedValue
    @Id
    private Long id;

    @Length(max=20)
    @NotBlank
    private String bezeichnung;

    @Length(max=200)
    @NotBlank
    private String fragetext;

    @NotNull
    @OneToMany
    private List<Antwort> antwortList;

    @NotNull
    private LocalDate ablaufdatum;

}
