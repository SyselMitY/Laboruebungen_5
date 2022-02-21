package cf.soisi.personinterest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Interest {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    @NotBlank
    private String description;
}
