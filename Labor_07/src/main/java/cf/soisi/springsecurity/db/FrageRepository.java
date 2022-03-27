package cf.soisi.springsecurity.db;

import cf.soisi.springsecurity.domain.Frage;
import cf.soisi.springsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FrageRepository extends JpaRepository<Frage,Long> {
    @Query("select f from Frage f where f not in (select a.frage from Antwort a where a.user = ?1) and f.ablaufdatum > current_date")
    List<Frage> findFrageByUserNotAnswered(User user);

}
