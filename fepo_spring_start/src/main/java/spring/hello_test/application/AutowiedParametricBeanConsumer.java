package spring.hello_test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutowiedParametricBeanConsumer {

    @Bean(initMethod = "print")
    @Autowired
    public AutowiredParametricBean autowiredParametricBean(@Value("${spring.servlet.multipart.location}") String s){
        return new AutowiredParametricBean(s);
    }
}
