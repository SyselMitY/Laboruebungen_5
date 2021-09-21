package cf.soisi.spring_wiederholung.repo;

import cf.soisi.spring_wiederholung.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

}
