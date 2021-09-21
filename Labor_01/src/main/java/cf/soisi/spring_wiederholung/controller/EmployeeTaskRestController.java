package cf.soisi.spring_wiederholung.controller;

import cf.soisi.spring_wiederholung.domain.Staff;
import cf.soisi.spring_wiederholung.repo.StaffRepository;
import cf.soisi.spring_wiederholung.repo.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
public class EmployeeTaskRestController {

    StaffRepository staffRepository;
    TaskRepository taskRepository;

    public EmployeeTaskRestController(StaffRepository staffRepository, TaskRepository taskRepository) {
        this.staffRepository = staffRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/employees")
    List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @GetMapping("/employees/id/{id}")
    Staff findStaffById(@PathVariable String id) {
        return staffRepository
                .findById(id)
                .orElseThrow();
    }

    @PostMapping("/employees")
    ResponseEntity<Staff> addEmployee(@RequestBody Staff toCreate) {
        Staff created = staffRepository.save(toCreate);
        //TODO fertig mocha
        return ResponseEntity.created()
    }
}
