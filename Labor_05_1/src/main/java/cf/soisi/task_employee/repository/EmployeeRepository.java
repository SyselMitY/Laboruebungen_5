package cf.soisi.task_employee.repository;

import cf.soisi.task_employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
