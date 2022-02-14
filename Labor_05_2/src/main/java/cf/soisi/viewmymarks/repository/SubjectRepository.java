package cf.soisi.viewmymarks.repository;

import cf.soisi.viewmymarks.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
}
