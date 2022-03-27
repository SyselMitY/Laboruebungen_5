package cf.soisi.springsecurity.controller;

import cf.soisi.springsecurity.auth.Roles;
import cf.soisi.springsecurity.db.AntwortRepository;
import cf.soisi.springsecurity.db.FrageRepository;
import cf.soisi.springsecurity.db.UserRepository;
import cf.soisi.springsecurity.domain.Antwort;
import cf.soisi.springsecurity.domain.Antwortmoeglichkeit;
import cf.soisi.springsecurity.domain.Frage;
import cf.soisi.springsecurity.domain.User;
import cf.soisi.springsecurity.util.FrageWithAntwortCounts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FrageController {

    private final FrageRepository frageRepository;
    private final UserRepository userRepository;
    private final AntwortRepository antwortRepository;

    public FrageController(FrageRepository frageRepository, UserRepository userRepository, AntwortRepository antwortRepository) {
        this.frageRepository = frageRepository;
        this.userRepository = userRepository;
        this.antwortRepository = antwortRepository;
    }

    @GetMapping("/add")
    public String add(Model model, Principal principal) {
        model.addAttribute("frage", new Frage());
        model.addAttribute("email", principal.getName());
        return "addquestion";
    }

    @PostMapping("/add")
    public String add(@Valid Frage frage, BindingResult bindingResult, Principal principal, Model model) {
        User loggedInUser = userRepository.findById(principal.getName()).orElseThrow();
        if (loggedInUser.getRole().equals(Roles.USER)) {
            model.addAttribute("error", "Bist ka admin, derfst ah nix hinzufügen");
            return "error";
        }
        if (bindingResult.hasErrors()) {
            return "addquestion";
        }
        frageRepository.save(frage);
        return "redirect:/fragen";
    }

    @GetMapping("/fragen")
    public String fragen(Model model, Principal principal) {
        final User loggedInUser = userRepository.findById(principal.getName()).orElseThrow();
        if (loggedInUser.getRole().equals(Roles.ADMIN))
            return fragenAdmin(model, loggedInUser);
        else
            return fragenNormalUser(model, loggedInUser);
    }

    private String fragenNormalUser(Model model, User user) {
        model.addAttribute("fragen", frageRepository.findFrageByUserNotAnswered(user));
        model.addAttribute("email", user.getEmail());
        return "questionlist";
    }

    private String fragenAdmin(Model model, User user) {
        final List<FrageWithAntwortCounts> fragenWithAntwortCount = frageRepository
                .findAll()
                .stream()
                .map(FrageWithAntwortCounts::new)
                .toList();
        model.addAttribute("fragen", fragenWithAntwortCount);
        model.addAttribute("email", user.getEmail());
        return "questionadmin";
    }

    @GetMapping("/fragen/{id}")
    public String frageDetail(Model model, @PathVariable Long id, Principal principal) {

        model.addAttribute("email", principal.getName());
        final Frage frage = frageRepository.findById(id).orElseThrow();
        model.addAttribute("question", frage);
        model.addAttribute("antwort", new Antwort(Antwortmoeglichkeit.TRIFFT_VOELLIG_ZU, frage, null));
        return "questiondetail";
    }

    @PostMapping("/fragen/{id}")
    public String frageAnswer(@Valid Antwort antwort, @PathVariable Long id, Principal principal, Model model) {
        final User loggedInUser = userRepository.findById(principal.getName()).orElseThrow();
        final Frage frage = frageRepository.findById(id).orElseThrow();
        if (frage.getAblaufdatum().isBefore(LocalDate.now())) {
            model.addAttribute("fehler", "De Frage is scho zoid zum beantworten");
            return "error";
        }
        if (loggedInUser.getRole().equals(Roles.ADMIN)) {
            model.addAttribute("fehler", "Ois Admin kaust du ned");
        }
        antwort.setUser(loggedInUser);
        antwort.setFrage(frage);
        antwortRepository.save(antwort);
        return "redirect:/fragen/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/fragen";
    }
}
