package cf.soisi.springsecurity.util;

import cf.soisi.springsecurity.domain.Antwort;
import cf.soisi.springsecurity.domain.Antwortmoeglichkeit;
import cf.soisi.springsecurity.domain.Frage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class FrageWithAntwortCounts {
    private final Long id;
    private final String bezeichnung;
    private final LocalDate ablaufdatum;
    private final Map<Antwortmoeglichkeit, Long> antworten;

    public FrageWithAntwortCounts(Frage frage) {
        this.id = frage.getId();
        this.bezeichnung = frage.getBezeichnung();
        this.ablaufdatum = frage.getAblaufdatum();
        this.antworten = frage.getAntwortList()
                .stream()
                .collect(Collectors.groupingBy(Antwort::getAntwortmoeglichkeit, Collectors.counting()));
        addMissingAntwortmoeglichkeiten();
    }

    private void addMissingAntwortmoeglichkeiten() {
        Arrays.stream(Antwortmoeglichkeit.values())
                .forEach(antwortmoeglichkeit -> antworten.putIfAbsent(antwortmoeglichkeit, 0L));
    }
}
