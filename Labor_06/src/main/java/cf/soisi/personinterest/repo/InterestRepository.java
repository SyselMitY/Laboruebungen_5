package cf.soisi.personinterest.repo;

import cf.soisi.personinterest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
}
