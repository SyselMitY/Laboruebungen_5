package cf.soisi.springsecurity.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Antwort {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    @NotNull
    private Antwortmoeglichkeit antwortmoeglichkeit;

    @ManyToOne(optional = false)
    private Frage frage;
}
