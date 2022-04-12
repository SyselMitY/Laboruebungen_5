package at.htlstp.restevents.db;

import at.htlstp.restevents.domain.Event;
import at.htlstp.restevents.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPersons(Person person);
}
