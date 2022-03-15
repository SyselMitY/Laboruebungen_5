package cf.soisi.spring_wiederholung.testpackage;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Tasks {
    private Object id;
    private String description;
    private Date finishedDate;
    private Integer hoursWorked;
    private Staff employee;

    @Id
    @Column(name = "id", nullable = false)
    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "finished_date", nullable = true)
    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Basic
    @Column(name = "hours_worked", nullable = true)
    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tasks tasks = (Tasks) o;

        if (id != null ? !id.equals(tasks.id) : tasks.id != null) return false;
        if (description != null ? !description.equals(tasks.description) : tasks.description != null) return false;
        if (finishedDate != null ? !finishedDate.equals(tasks.finishedDate) : tasks.finishedDate != null) return false;
        if (hoursWorked != null ? !hoursWorked.equals(tasks.hoursWorked) : tasks.hoursWorked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (finishedDate != null ? finishedDate.hashCode() : 0);
        result = 31 * result + (hoursWorked != null ? hoursWorked.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    public Staff getEmployee() {
        return employee;
    }

    public void setEmployee(Staff employee) {
        this.employee = employee;
    }
}
