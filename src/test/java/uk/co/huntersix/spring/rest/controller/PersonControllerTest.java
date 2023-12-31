package uk.co.huntersix.spring.rest.controller;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }

    @Test
    public void shouldReturnNotFound_whenPersonNotExist() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(null);
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Person not found [mary smith]")));
    }

    @Test
    public void shouldReturnPersonByLastName() throws Exception {
        when(personDataService.findPersonByLastName(any())).thenReturn(
            asList(new Person("Mary", "Smith"), new Person("Aaron", "Smith")));
        this.mockMvc.perform(get("/person/smith"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].id").exists())
            .andExpect(jsonPath("[0].firstName").value("Mary"))
            .andExpect(jsonPath("[0].lastName").value("Smith"))
            .andExpect(jsonPath("[1].id").exists())
            .andExpect(jsonPath("[1].firstName").value("Aaron"))
            .andExpect(jsonPath("[1].lastName").value("Smith"));
    }

    @Test
    public void shouldReturnNotFound_whenPersonNotExistByLastName() throws Exception {
        when(personDataService.findPersonByLastName(any())).thenReturn(null);
        this.mockMvc.perform(get("/person/smith"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Person not found [smith]")));
    }

    @Test
    public void shouldAddPerson() throws Exception {
        when(personDataService.addPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(post("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }

    @Test
    public void shouldNotAddPerson_whenPersonExists() throws Exception {
        when(personDataService.addPerson(any(), any())).thenReturn(null);
        this.mockMvc.perform(post("/person/smith/aaron"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Person already exists [aaron smith]")));
    }
}