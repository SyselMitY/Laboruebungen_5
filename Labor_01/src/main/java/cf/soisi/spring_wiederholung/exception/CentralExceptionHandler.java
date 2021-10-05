package cf.soisi.spring_wiederholung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CentralExceptionHandler {
    @ExceptionHandler(StaffAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleStaffExists(StaffAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(StaffNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(StaffNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DateOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDateOrder(DateOrderException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidArgument(MethodArgumentNotValidException e) {
        return "Invalid Parameter(s):\n" +
                e.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(ObjectError::toString)
                        .collect(Collectors.joining("\n"));
    }
}
