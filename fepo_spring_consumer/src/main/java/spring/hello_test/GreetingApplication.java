package spring.hello_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GreetingApplication {


    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(new Class[]{GreetingApplication.class}, args);
        System.out.println("consumer started");
    }
}
