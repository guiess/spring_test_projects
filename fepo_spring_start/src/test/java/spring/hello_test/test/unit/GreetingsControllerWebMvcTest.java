package spring.hello_test.test.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import spring.hello_test.controller.GreetingController;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.service.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//secure option is to overcome security if enabled
@WebMvcTest(value = GreetingController.class, secure = false)
public class GreetingsControllerWebMvcTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService customerService;


    @Test
    public void greetingsController_getAllCustomers() throws Exception{
        Customer testCustomer = new Customer("Isaak", "Asimov");
        List<Customer> allCustomers = Arrays.asList(testCustomer);

        given(customerService.findAll()).willReturn(allCustomers);

        mvc.perform(get("/getAllCustomers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(testCustomer.getFirstName())));

    }
}
