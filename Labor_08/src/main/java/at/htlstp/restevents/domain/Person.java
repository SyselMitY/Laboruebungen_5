package at.htlstp.restevents.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@Table(name = "persons")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(name = "person_name")
    private String name;

    @ManyToMany(mappedBy = "persons")
    private List<Event> events;
}
