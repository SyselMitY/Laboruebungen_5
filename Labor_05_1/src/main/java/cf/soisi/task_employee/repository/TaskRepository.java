package cf.soisi.task_employee.repository;

import cf.soisi.task_employee.domain.Employee;
import cf.soisi.task_employee.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByEmployee(Employee employee);
}
