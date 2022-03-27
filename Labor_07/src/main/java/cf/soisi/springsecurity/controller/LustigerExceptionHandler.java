package cf.soisi.springsecurity.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class LustigerExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e, Model model) {
        model.addAttribute("fehler","I schätz du host die Frage schon moi beantwortet oder so.");
        return "error";
    }
}
