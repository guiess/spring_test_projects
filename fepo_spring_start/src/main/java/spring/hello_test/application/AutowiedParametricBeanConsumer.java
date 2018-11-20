package spring.hello_test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AutowiedParametricBeanConsumer {

    @Bean(initMethod = "print")
    //without next 2 annotations bean instantiated with the direct call can be autowired later smwr else
    //@Autowired
    //@Scope("prototype") //adding of this annotation will cause disabling of auto-instantiation by autowiring
    public AutowiredParametricBean autowiredParametricBean(@Value("${spring.servlet.multipart.location}") String s){
        return new AutowiredParametricBean(s);
    }
}
