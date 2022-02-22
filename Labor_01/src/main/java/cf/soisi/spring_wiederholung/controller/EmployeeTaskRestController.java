package cf.soisi.spring_wiederholung.controller;

import cf.soisi.spring_wiederholung.domain.Staff;
import cf.soisi.spring_wiederholung.domain.Task;
import cf.soisi.spring_wiederholung.exception.DateOrderException;
import cf.soisi.spring_wiederholung.exception.StaffAlreadyExistsException;
import cf.soisi.spring_wiederholung.exception.StaffNotFoundException;
import cf.soisi.spring_wiederholung.repo.StaffRepository;
import cf.soisi.spring_wiederholung.repo.TaskRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class EmployeeTaskRestController {

    private final StaffRepository staffRepository;
    private final TaskRepository taskRepository;

    public EmployeeTaskRestController(StaffRepository staffRepository, TaskRepository taskRepository) {
        this.staffRepository = staffRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/employees")
    List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @GetMapping("/employees/id/{id}")
    Staff findStaffById(@PathVariable @NotBlank String id) throws StaffNotFoundException {
        return staffRepository
                .findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));
    }

    @PostMapping("/employees")
    ResponseEntity<Staff> addEmployee(@RequestBody @Valid Staff toCreate) throws StaffAlreadyExistsException {

        if (staffRepository.existsById(toCreate.getId()))
            throw new StaffAlreadyExistsException(toCreate.getId());

        Staff created = staffRepository.save(toCreate);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/employees/id/{id}")
                .build(created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping("/employees/{id}/hoursWorked")
    Integer getHoursWorked(@PathVariable String id) throws StaffNotFoundException {
        Staff staff = staffRepository
                .findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));

        return taskRepository
                .getAllByStaff(staff)
                .stream()
                .mapToInt(Task::getHoursWorked)
                .sum();
    }

    @GetMapping("/employees/{id}/tasks")
    List<Task> getTaskFromTimeframe(@PathVariable String id,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to)
            throws StaffNotFoundException, DateOrderException {

        Staff staff = staffRepository
                .findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));

        if (from.isAfter(to))
            throw new DateOrderException("DateFrom must not be after DateTo");

        return taskRepository.getAllByStaffAndFinishedDateBetween(staff, from, to);
    }

}
