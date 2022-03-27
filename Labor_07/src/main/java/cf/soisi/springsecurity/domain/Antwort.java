package cf.soisi.springsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"frage_id", "user_email"})})
public class Antwort {
    public Antwort(Antwortmoeglichkeit antwortmoeglichkeit, Frage frage, User user) {
        this.antwortmoeglichkeit = antwortmoeglichkeit;
        this.frage = frage;
        this.user = user;
        this.frage.getAntwortList().add(this);
    }

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    @NotNull
    private Antwortmoeglichkeit antwortmoeglichkeit;

    @ManyToOne(optional = false)
    private Frage frage;

    @ManyToOne(optional = false)
    private User user;
}
