package cf.soisi.spring_wiederholung.exception;

public class StaffNotFoundException extends Exception {
    public StaffNotFoundException(String id) {
        super("Staff with id " + id + " was not found");
    }
}
