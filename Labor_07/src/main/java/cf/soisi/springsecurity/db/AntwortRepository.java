package cf.soisi.springsecurity.db;

import cf.soisi.springsecurity.domain.Antwort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntwortRepository extends JpaRepository<Antwort, Long> {
}
