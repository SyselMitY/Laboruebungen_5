package cf.soisi.task_employee;

import cf.soisi.task_employee.domain.Employee;
import cf.soisi.task_employee.repository.EmployeeRepository;
import cf.soisi.task_employee.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontendController {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public FrontendController(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String employeeDropdownPage(Model model) {
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "employee-dropdown";
    }

    @GetMapping("/tasks")
    public String taskListPage(Model model,@RequestParam String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        model.addAttribute("taskList", taskRepository.findAllByEmployee(employee));
        return "task-list";
    }
}
