package cf.soisi.springsecurity.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Frage {
    public Frage(String bezeichnung, String fragetext,  LocalDate ablaufdatum) {
        this.bezeichnung = bezeichnung;
        this.fragetext = fragetext;
        this.antwortList = new ArrayList<>();
        this.ablaufdatum = ablaufdatum;
    }

    @GeneratedValue
    @Id
    private Long id;

    @Length(max=20)
    @NotBlank
    private String bezeichnung;

    @Length(max=200)
    @NotBlank
    private String fragetext;

    @OneToMany(mappedBy = "frage")
    private List<Antwort> antwortList;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ablaufdatum;

}
