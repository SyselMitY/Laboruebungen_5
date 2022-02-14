package cf.soisi.viewmymarks.components;

import cf.soisi.viewmymarks.domain.Grade;
import cf.soisi.viewmymarks.domain.Subject;
import cf.soisi.viewmymarks.repository.GradeRepository;
import cf.soisi.viewmymarks.repository.StudentRepository;
import cf.soisi.viewmymarks.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Component
public class GradeCsvReader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final GradeRepository gradeRepository;

    public GradeCsvReader(GradeRepository gradeRepository,
                          SubjectRepository subjectRepository,
                          StudentRepository studentRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    private Grade getGradeFromCsvLine(String s) {

        String[] values = s.split(",");
        String studentId = values[0];
        String date = values[1];
        String subjectId = values[2];
        String grade = values[3];

        return new Grade(
                Integer.parseInt(grade),
                LocalDate.parse(date),
                getOrCreateSubject(subjectId),
                studentRepository.findById(studentId).orElseThrow()
        );
    }

    private Subject getOrCreateSubject(String subjectId) {
        final Optional<Subject> existingSubject = subjectRepository.findById(subjectId);
        if (existingSubject.isPresent())
            return existingSubject.get();
        Subject newSubject = new Subject(subjectId);
        subjectRepository.save(newSubject);
        return newSubject;
    }

    @Override
    public void run(String... args) {

        new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(GradeCsvReader.class.getResourceAsStream("/grades.csv"))))
                .lines()
                .skip(1)
                .map(this::getGradeFromCsvLine)
                .forEach(gradeRepository::save);

    }
}
