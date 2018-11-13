package spring.hello_test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJDBCRepository;
import spring.hello_test.repository.CustomerJPARepository;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration
@EnableScheduling
public class TestDataGenerator {

    @Autowired
    CustomerJPARepository repository;
    @Autowired
    CustomerJDBCRepository customerJDBCRepository;

    @Value("${spring.hello_test.generate-test-data:false}")
    private Boolean isNeedToGenerate;

    @PostConstruct
    public void generate(){
        if(!isNeedToGenerate) return;

        jpaFillDB();
        jdbcFillDB();
    }

    private void jpaFillDB(){
        System.out.println("DBDataGerenator jpaFillDB start");
        for(int i=0; i<10; i++) {
            Customer cst = new Customer("name" + i, "surname" + i);
            repository.save(cst);
            System.out.println("DBDataGerenator "+cst);
        }
        System.out.println("DBDataGerenator jpaFillDB end");
    }

    private void jdbcFillDB(){
        customerJDBCRepository.runJDBCandFillWithData();
    }
}
