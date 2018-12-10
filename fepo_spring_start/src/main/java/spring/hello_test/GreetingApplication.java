package spring.hello_test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import spring.hello_test.application.TestDataGenerator;

@Configuration
@SpringBootApplication
/*@EntityScan("spring.hello_test.model.entity")
@EnableJpaRepositories("spring.hello_test.repository")*/

public class GreetingApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(GreetingApplication.class);

    public static void main(String[] args){
        LOGGER.debug("GreetingApplication starting");
        ConfigurableApplicationContext context =
                SpringApplication.run(new Class[]{GreetingApplication.class
                        //, ScheduledCustomerUpdateTask.class
                        //, FileUploadApplication.class
                        //, TestDataGenerator.class
                }, args);
        System.out.println("FEPO GreetingApplication started");
        LOGGER.debug("GreetingApplication started");
    }

}
