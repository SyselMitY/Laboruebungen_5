package cf.soisi.viewmymarks.repository;

import cf.soisi.viewmymarks.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
