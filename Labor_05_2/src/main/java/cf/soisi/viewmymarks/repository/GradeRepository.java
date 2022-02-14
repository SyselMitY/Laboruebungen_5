package cf.soisi.viewmymarks.repository;

import cf.soisi.viewmymarks.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    public List<Grade> findAllByStudentId(String studentId);
}
