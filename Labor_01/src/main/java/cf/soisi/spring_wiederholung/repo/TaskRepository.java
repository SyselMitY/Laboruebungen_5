package cf.soisi.spring_wiederholung.repo;

import cf.soisi.spring_wiederholung.domain.Staff;
import cf.soisi.spring_wiederholung.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    public List<Task> getAllByStaff(Staff staff);

    public List<Task> getAllByStaffAndFinishedDateBetween(Staff staff, LocalDate from, LocalDate to);

}
