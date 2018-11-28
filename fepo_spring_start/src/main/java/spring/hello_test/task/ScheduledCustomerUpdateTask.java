package spring.hello_test.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableAutoConfiguration
@EnableScheduling
public class ScheduledCustomerUpdateTask {

    @Autowired
    CustomerService customerService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Scheduled(fixedDelay = 5000)
    public void updateCustomerWithActualDate(){
        System.out.println("Current time "+DATE_FORMAT.format(new Date()));
        customerService.setCurrentDate(1L, new Date());
    }
}
