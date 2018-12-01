package spring.hello_test.test.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;
import spring.hello_test.service.CustomerServiceImpl;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CustomerServiceMockTest {

    Logger logger = LoggerFactory.getLogger(CustomerServiceMockTest.class);

    @TestConfiguration
    static class CustomerServiceMockTestContextConfiguration{

        @Bean
        public CustomerService customerService(){
            return new CustomerServiceImpl();
        }
    }

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerJPARepository customerJPARepository;

    @Before
    public void repositoryConfiguration(){
        Customer stubCustomer = new Customer("Ted", "Chan");
        Mockito.when(customerJPARepository.findCustomerByLastName(stubCustomer.getLastName()))
                .thenReturn(stubCustomer);
    }

    @Test
    public void setCustomerService_findCustomerByLastName(){
        Customer foundCustomer = customerService.findCustomerByLastName("Chan");
        System.out.println("mocked customer "+ foundCustomer);
        assertThat(foundCustomer.getFirstName()).isEqualTo("Ted");
    }
}
