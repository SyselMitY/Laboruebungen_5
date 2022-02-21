package cf.soisi.personinterest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Interest {

    public Interest(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    @NotBlank
    private String description;
}
