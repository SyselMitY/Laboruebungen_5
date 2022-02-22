package cf.soisi.viewmymarks.controller;

import cf.soisi.viewmymarks.domain.Grade;
import cf.soisi.viewmymarks.domain.Student;
import cf.soisi.viewmymarks.domain.Subject;
import cf.soisi.viewmymarks.repository.GradeRepository;
import cf.soisi.viewmymarks.repository.StudentRepository;
import cf.soisi.viewmymarks.repository.SubjectRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class VmmController {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;

    public VmmController(StudentRepository studentRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/")
    public String studentList(Model model) {
        model.addAttribute("students",studentRepository.findAll());
        return "student-list";
    }

    @GetMapping("/grade-list")
    public String gradeList(Model model,@RequestParam String id) {
        Student student = studentRepository.findById(id).orElseThrow();
        model.addAttribute("student",student);
        model.addAttribute("subjects",subjectRepository.findAll());
        return "grade-list";
    }

    @PostMapping("/grade")
    public String saveGrade(@RequestParam String subject,
                            @RequestParam String studentId,
                            @RequestParam Integer gradeRating,
                            @RequestParam String date) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Subject existingSubject = subjectRepository.findById(subject).orElseThrow();
        Grade grade = new Grade(gradeRating,LocalDate.parse(date),existingSubject,student);
        gradeRepository.save(grade);
        return "redirect:/grade-list?id=" + grade.getStudent().getId();
    }

}
