package cf.soisi.personinterest.controller;

import cf.soisi.personinterest.domain.Person;
import cf.soisi.personinterest.repo.InterestRepository;
import cf.soisi.personinterest.repo.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.BindException;

@Controller
public class InterestController {

    InterestRepository interestRepository;
    PersonRepository personRepository;

    public InterestController(InterestRepository interestRepository, PersonRepository personRepository) {
        this.interestRepository = interestRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public String personList(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "person-list";
    }

    @GetMapping("/add")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("interests", interestRepository.findAll());
        return "add-person";
    }

    @PostMapping("/add")
    public String addPerson(@Valid Person person, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("interests", interestRepository.findAll());
            return "add-person";
        }
        personRepository.save(person);
        return "redirect:/";
    }

}