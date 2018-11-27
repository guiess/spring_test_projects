package spring.hello_test.test.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataJPARespositoryTest {

    private Logger LOGGER = LoggerFactory.getLogger(DataJPARespositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerJPARepository repository;

    @Test
    public void findCustomerByLastName(){
        LOGGER.debug("DataJPARespositoryTest findCustomerByLastName start");
        Customer test = new Customer("Arthur", "Clark");
        entityManager.persist(test);
        entityManager.flush();

        Customer found = repository.findCustomerByLastName("Clark");
        assertThat(found.getFirstName()).isEqualTo(test.getFirstName());
        LOGGER.debug("DataJPARespositoryTest findCustomerByLastName end");
    }

}
