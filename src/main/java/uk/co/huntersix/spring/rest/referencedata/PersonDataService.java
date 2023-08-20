package uk.co.huntersix.spring.rest.referencedata;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService {

    Logger logger = LoggerFactory.getLogger(PersonDataService.class);

    public static final List<Person> PERSON_DATA = Arrays.asList(
        new Person("Mary", "Smith"),
        new Person("Brian", "Archer"),
        new Person("Collin", "Brown")
    );

    public Person findPerson(String lastName, String firstName) {
        Predicate<Person> firstNameEqual = p -> p.getFirstName().equalsIgnoreCase(firstName);
        Predicate<Person> lastNameEqual = p -> p.getLastName().equalsIgnoreCase(lastName);

        List<Person> personList = PERSON_DATA.stream()
            .filter(firstNameEqual.and(lastNameEqual))
            .collect(toList());

        if (isEmpty(personList)) {
            logger.info("person not found [{} {}]", lastName, firstName);
            return null;
        }

        return personList.get(0);
    }
}
