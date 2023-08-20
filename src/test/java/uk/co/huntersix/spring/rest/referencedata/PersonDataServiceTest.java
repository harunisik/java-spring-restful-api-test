package uk.co.huntersix.spring.rest.referencedata;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

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
}
