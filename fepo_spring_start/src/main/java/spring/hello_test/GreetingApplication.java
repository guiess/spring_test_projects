package spring.hello_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import spring.hello_test.repository.CustomerJDBCRepository;
import spring.hello_test.repository.CustomerRepository;
import spring.hello_test.task.ScheduledCustomerUpdateTask;
import spring.hello_test.test_data.DBDataGerenator;

@SpringBootApplication
public class GreetingApplication {

    public static CustomerRepository repository;

    private static CustomerJDBCRepository customerJDBCRepository;

    public static void main(String[] args){

        /*start restFull app with JPA*/
        ConfigurableApplicationContext context =
                SpringApplication.run(new Class[]{GreetingApplication.class
                        //, ScheduledCustomerUpdateTask.class
                }, args);
        System.out.println("FEPO GreetingApplication start filling db");
        repository = context.getBean(CustomerRepository.class);
        new DBDataGerenator(repository).call();
        System.out.println("FEPO GreetingApplication end filling db");
        /*end restFull app with JPA*/

        customerJDBCRepository = context.getBean(CustomerJDBCRepository.class);
        customerJDBCRepository.runJDBCandFillWithData();
        /*start Scheduled task*/
        //ConfigurableApplicationContext context = SpringApplication.run(ScheduledCustomerUpdateTask.class, args);

        /*end Scheduled task*/

    }

}
