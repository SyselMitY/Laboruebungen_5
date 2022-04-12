package at.htlstp.restevents.controller;

import at.htlstp.restevents.db.EventRepository;
import at.htlstp.restevents.db.PersonRepository;
import at.htlstp.restevents.domain.Event;
import at.htlstp.restevents.domain.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EventsRestController {

    private final EventRepository eventRepository;
    private final PersonRepository personRepository;

    public EventsRestController(EventRepository eventRepository, PersonRepository personRepository) {
        this.eventRepository = eventRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/persons/events")
    public List<Event> getEventsFromPerson(@RequestParam long personId) {
        Person person = personRepository.findById(personId).orElseThrow();
        return eventRepository.findByPersons(person);
    }

    @PostMapping("/persons/{personId}/events")
    public ResponseEntity<List<Event>> addEventToPerson(@PathVariable long personId, @RequestBody Event event) {
        Person person = personRepository.findById(personId).orElseThrow();
        Event existingEvent = eventRepository.findById(event.getId()).orElseThrow();

        List<Event> events = person.getEvents();
        if (checkOverlap(existingEvent, events)) {
            return ResponseEntity.badRequest().build();
        }

        existingEvent.addPerson(person);
        eventRepository.save(existingEvent);

        URI uri = createUriWithId("/persons/{personId}/events", personId);
        return ResponseEntity.created(uri).body(person.getEvents());
    }

    @GetMapping("/events/{eventId}")
    public Event getEvent(@PathVariable long eventId) {
        return eventRepository.findById(eventId).orElseThrow();
    }

    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {

        if (event.getEnd().isBefore(event.getBegin())) {
            return ResponseEntity.badRequest().build();
        }

        Event saved = eventRepository.save(event);
        URI uri = createUriWithId("/events/{eventId}", saved.getId());
        return ResponseEntity.created(uri).body(saved);
    }

    private URI createUriWithId(String s, Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(s)
                .build(id);
    }

    private boolean checkOverlap(Event event, List<Event> events) {
        for (Event e : events) {
            if (e.getBegin().isBefore(event.getEnd()) &&
                    e.getEnd().isAfter(event.getBegin())) {
                return true;
            }
        }
        return false;
    }

}
