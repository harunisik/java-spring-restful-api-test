package uk.co.huntersix.spring.rest.controller;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> person(@PathVariable(value="lastName") String lastName,
        @PathVariable(value="firstName") String firstName) {
        Person person = personDataService.findPerson(lastName, firstName);

        if(isNull(person)) {
            throw new ResponseStatusException(NOT_FOUND, format("Person not found [%s %s]", firstName, lastName));
        }

        return ok(person);
    }

    @PostMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> addPerson(@PathVariable(value="lastName") String lastName,
        @PathVariable(value="firstName") String firstName) {
        Person person = personDataService.addPerson(lastName, firstName);

        if(isNull(person)) {
            throw new ResponseStatusException(BAD_REQUEST, format("Person already exists [%s %s]", firstName, lastName));
        }

        return ok(person);
    }

    @GetMapping("/person/{lastName}")
    public ResponseEntity<Collection<Person>> personByLastName(@PathVariable(value="lastName") String lastName) {
        List<Person> person = personDataService.findPersonByLastName(lastName);

        if(isNull(person)) {
            throw new ResponseStatusException(NOT_FOUND, format("Person not found [%s]", lastName));
        }

        return ok(person);
    }
}