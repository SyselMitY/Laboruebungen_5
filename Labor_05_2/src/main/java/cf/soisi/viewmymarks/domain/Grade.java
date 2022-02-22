package cf.soisi.viewmymarks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    public Grade(Integer grade, LocalDate date, Subject subject, Student student) {
        this.grade = grade;
        this.date = date;
        this.subject = subject;
        this.student = student;
        student.getGrades().add(this);
    }

    @Id
    @GeneratedValue
    private Long id;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer grade;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private Student student;

    @Override
    public String toString() {
        return String.valueOf(grade);
    }
}

