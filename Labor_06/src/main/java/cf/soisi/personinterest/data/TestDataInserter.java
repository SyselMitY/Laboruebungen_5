package cf.soisi.personinterest.data;

import cf.soisi.personinterest.domain.Interest;
import cf.soisi.personinterest.domain.Person;
import cf.soisi.personinterest.domain.Sex;
import cf.soisi.personinterest.repo.InterestRepository;
import cf.soisi.personinterest.repo.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class TestDataInserter {

    private static final List<Interest> interests = List.of(
            new Interest("Photography"),
            new Interest("Music"),
            new Interest("Trains"),
            new Interest("City planning"),
            new Interest("Infrastructure"),
            new Interest("Reading"),
            new Interest("Cooking")
    );

    //Persons: generate data
    private static final Set<Person> persons = Set.of(
            new Person("Peter",
                    "Sysel",
                    LocalDate.of(2003, 4, 18),
                    Sex.MALE,
                    interests.get(0), interests.get(3), interests.get(4)),
            new Person("Herbert",
                    "Oberbuglbauer",
                    LocalDate.of(2000, 8, 24),
                    Sex.MALE)
    );

    public TestDataInserter(PersonRepository personRepository, InterestRepository interestRepository) {
        interestRepository.saveAll(interests);
        personRepository.saveAll(persons);
    }
}
