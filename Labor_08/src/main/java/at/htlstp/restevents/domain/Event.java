package at.htlstp.restevents.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aspectj.lang.annotation.Before;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "events")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(name = "event_name")
    private String name;

    @Column(name = "begin_time")
    @NotNull
    private LocalDateTime begin;

    @Column(name = "end_time")
    @NotNull
    private LocalDateTime end;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "persons_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;


    public void addPerson(Person person) {
        persons.add(person);
        person.getEvents().add(this);
    }
}
