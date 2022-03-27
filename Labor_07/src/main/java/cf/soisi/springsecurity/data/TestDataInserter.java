package cf.soisi.springsecurity.data;

import cf.soisi.springsecurity.auth.Roles;
import cf.soisi.springsecurity.db.AntwortRepository;
import cf.soisi.springsecurity.db.FrageRepository;
import cf.soisi.springsecurity.db.UserRepository;
import cf.soisi.springsecurity.domain.Antwort;
import cf.soisi.springsecurity.domain.Frage;
import cf.soisi.springsecurity.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Objects;

import static cf.soisi.springsecurity.domain.Antwortmoeglichkeit.*;

@Configuration
public class TestDataInserter implements CommandLineRunner {
    private final UserRepository userRepository;
    private final FrageRepository frageRepository;
    private final PasswordEncoder passwordEncoder;
    private final AntwortRepository antwortRepository;

    public TestDataInserter(UserRepository userRepository,
                            FrageRepository frageRepository,
                            PasswordEncoder passwordEncoder,
                            AntwortRepository antwortRepository) {
        this.userRepository = userRepository;
        this.frageRepository = frageRepository;
        this.passwordEncoder = passwordEncoder;
        this.antwortRepository = antwortRepository;
    }

    @Override
    public void run(String... args) {
        List<User> users = List.of(
                new User("admin@soisi.cf", Roles.ADMIN, passwordEncoder.encode("admin")),
                new User("user1@soisi.cf", Roles.USER, passwordEncoder.encode("user1")),
                new User("user2@soisi.cf", Roles.USER, passwordEncoder.encode("user2")),
                new User("user3@soisi.cf", Roles.USER, passwordEncoder.encode("user3")));
        userRepository.saveAll(users);
        List<Frage> fragen = List.of(
                new Frage("Schraubenzieher", "Wie viele Schrauben kann ein Schraubenzieher schrauben?", LocalDate.now().minusDays(30)),
                new Frage("TCP/IP", "Wie viele Ports hat ein TCP/IP-Netzwerk?", LocalDate.now().plusDays(30)),
                new Frage("Java", "Wie viele Klassen hat Java?", LocalDate.now().plusDays(30)),
                new Frage("Rubiks Cube", "Weißt du, wie viele Rubiks-Kubik-Klassen gibt es?", LocalDate.now().plusDays(30)),
                new Frage("Sinn", "Ergibt es Sinn, dass die Fragen nicht mit Freitext beantwortet werden?", LocalDate.now().plusDays(30)));
        frageRepository.saveAll(fragen);
        List<Antwort> antworten = List.of(
                new Antwort(TRIFFT_NICHT_ZU, fragen.get(0), users.get(1)),
                new Antwort(TRIFFT_TEILWEISE_ZU, fragen.get(0), users.get(2)),
                new Antwort(TRIFFT_VOELLIG_ZU, fragen.get(1), users.get(1)));
        antwortRepository.saveAll(antworten);
    }

    public UserRepository userRepository() {
        return userRepository;
    }

    public FrageRepository frageRepository() {
        return frageRepository;
    }

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    public AntwortRepository antwortRepository() {
        return antwortRepository;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TestDataInserter) obj;
        return Objects.equals(this.userRepository, that.userRepository) &&
                Objects.equals(this.frageRepository, that.frageRepository) &&
                Objects.equals(this.passwordEncoder, that.passwordEncoder) &&
                Objects.equals(this.antwortRepository, that.antwortRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository, frageRepository, passwordEncoder, antwortRepository);
    }

    @Override
    public String toString() {
        return "TestDataInserter[" +
                "userRepository=" + userRepository + ", " +
                "frageRepository=" + frageRepository + ", " +
                "passwordEncoder=" + passwordEncoder + ", " +
                "antwortRepository=" + antwortRepository + ']';
    }

}
