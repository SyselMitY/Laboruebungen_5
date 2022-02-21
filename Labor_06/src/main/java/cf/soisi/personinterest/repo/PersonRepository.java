package cf.soisi.personinterest.repo;

import cf.soisi.personinterest.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
