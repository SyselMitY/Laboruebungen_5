package cf.soisi.springsecurity.db;

import cf.soisi.springsecurity.domain.Frage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrageRepository extends JpaRepository<Frage,Long> {
}
