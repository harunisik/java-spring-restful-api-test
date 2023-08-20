package uk.co.huntersix.spring.rest.referencedata;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService {

    Logger logger = LoggerFactory.getLogger(PersonDataService.class);

    public final List<Person> PERSON_DATA = new ArrayList<>();

    @PostConstruct
    public void onPropertiesSet() {
        PERSON_DATA.add(new Person("Mary", "Smith"));
        PERSON_DATA.add(new Person("Brian", "Archer"));
        PERSON_DATA.add(new Person("Collin", "Brown"));
        PERSON_DATA.add(new Person("Aaron", "Smith"));
    }

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

    public List<Person> findPersonByLastName(String lastName) {
        Predicate<Person> lastNameEqual = p -> p.getLastName().equalsIgnoreCase(lastName);

        List<Person> personList = PERSON_DATA.stream()
            .filter(lastNameEqual)
            .collect(toList());

        if (isEmpty(personList)) {
            logger.info("person not found by lastName [{}]", lastName);
            return null;
        }

        return personList;
    }

    public Person addPerson(String lastName, String firstName) {
        Person person = findPerson(lastName, firstName);

        if(isNull(person)) {
            return createPerson(lastName, firstName);
        }

        logger.info("person already exists [{} {}]", firstName, lastName);
        return null;
    }

    private Person createPerson(String lastName, String firstName) {
        Person person = new Person(firstName, lastName);
        PERSON_DATA.add(person);
        return person;
    }


}
