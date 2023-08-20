package uk.co.huntersix.spring.rest.referencedata.model;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
public class PersonDataServiceTest {

    @InjectMocks
    private PersonDataService personDataService;

    @Before
    public void init() {
        personDataService.onPropertiesSet();
    }

    @Test
    public void shouldFindPerson() {

        String firstName = "Mary";
        String lastName = "Smith";

        Person person = personDataService.findPerson(lastName, firstName);

        assertNotNull(person);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }

    @Test
    public void shouldReturnNull_whenPersonNotFound() {

        String firstName = "unknown";
        String lastName = "unknown";

        Person person = personDataService.findPerson(lastName, firstName);

        assertNull(person);
    }

    @Test
    public void shouldFindPersonByLastName_whenOneMatches() {

        String lastName = "Brown";

        List<Person> personList = personDataService.findPersonByLastName(lastName);

        assertNotNull(personList);
        assertEquals(1, personList.size());
        assertEquals(lastName, personList.get(0).getLastName());
    }

    @Test
    public void shouldFindPersonByLastName_whenMultipleMatches() {

        String lastName = "Smith";

        List<Person> personList = personDataService.findPersonByLastName(lastName);

        assertNotNull(personList);
        assertEquals(2, personList.size());
        assertEquals(lastName, personList.get(0).getLastName());
        assertEquals(lastName, personList.get(1).getLastName());
    }

    @Test
    public void shouldReturnNull_whenPersonNotFoundByLastName() {

        String lastName = "unknown";

        List<Person> person = personDataService.findPersonByLastName(lastName);

        assertNull(person);
    }

    @Test
    public void shouldAddPerson() {

        String firstName = "lisa";
        String lastName = "smith";

        Person person = personDataService.addPerson(lastName, firstName);

        assertNotNull(person);
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
    }

    @Test
    public void shouldNotAddPerson_whenPersonExists() {

        String firstName = "Aaron";
        String lastName = "Smith";

        Person person = personDataService.addPerson(lastName, firstName);

        assertNull(person);
    }
}
