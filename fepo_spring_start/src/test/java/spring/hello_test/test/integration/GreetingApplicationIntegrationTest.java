package spring.hello_test.test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import spring.hello_test.GreetingApplication;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = GreetingApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
public class GreetingApplicationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerJPARepository repository;

    @Autowired
    CustomerService customerService;

    private void addTestCustomer(String firstName, String lastName){
        customerService.saveCustomer(new Customer(firstName, lastName));
    }

    @Test
    public void getAllCustomers_Test() throws Exception{
        addTestCustomer("Ray", "Bradburry");

        mockMvc.perform(get("/getAllCustomers")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("Ray")));
    }

    @Test
    public void getAllCustomersView_Test() throws Exception{
        addTestCustomer("Ray", "Bradburry");

        mockMvc.perform(get("/getAllCustomersView")
                .contentType("text/html;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bradburry")));
    }


}
