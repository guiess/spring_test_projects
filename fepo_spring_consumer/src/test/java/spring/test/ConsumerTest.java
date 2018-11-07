package spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import spring.hello_test.controller.GreetingController;
import spring.hello_test.model.entity.CustomerList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=GreetingController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class ConsumerTest {

    @Autowired
    GreetingController greetingController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoadCheck(){
        assertThat(greetingController).isNotNull();
        System.out.println("contextLoadCheck OK");
    }

    @Test
    public void providerGetCustomersCheck(){
        assertThat(this.testRestTemplate.
                getForEntity("http://localhost:8080/getAllCustomersAsObject", CustomerList.class).getBody().getCustomers()).asList().hasSize(10);
        System.out.println("providerGetCustomersCheck OK");
    }

    @Test
    public void consumerGetCustomersCheck(){
        assertThat(this.testRestTemplate.
                getForEntity("http://localhost:7777/getAllCustomersFormProvider", CustomerList.class).getBody().getCustomers()).asList().hasSize(10);
        System.out.println("consumerGetCustomersCheck OK");
    }

}