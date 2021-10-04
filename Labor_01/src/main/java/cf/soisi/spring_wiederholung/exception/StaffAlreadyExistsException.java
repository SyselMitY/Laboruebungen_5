package cf.soisi.spring_wiederholung.exception;

import org.hibernate.validator.constraints.Length;

public class StaffAlreadyExistsException extends Exception {
    public StaffAlreadyExistsException(@Length(max = 6) String id) {
        super("Staff with id " + id + " already exists");
    }
}
