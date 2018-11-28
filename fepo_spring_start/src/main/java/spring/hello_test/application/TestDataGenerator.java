package spring.hello_test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.ContextLoader;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJDBCRepository;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration
@EnableScheduling
public class TestDataGenerator {

    /*@Autowired
    CustomerJPARepository repository;*/
    @Autowired
    CustomerJDBCRepository customerJDBCRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    AutowiedParametricBeanConsumer autowiedParametricBeanConsumer;
    /*@Autowired
    AutowiredParametricBean autowiredParametricBean;*/

    @Value("${spring.hello_test.generate-test-data:false}")
    private Boolean isNeedToGenerate;

    @PostConstruct
    public void generate(){
        if(!isNeedToGenerate) return;

        jpaFillDB();
        //jdbcFillDB();

        //2 types of  instantiation of parametric bean
        //both will cause disabling of auto-instantiation during autowiring
        autowiedParametricBeanConsumer.autowiredParametricBean("direct call");
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(AutowiedParametricBeanConsumer.class);
        //whis won't properly work without @Scope("prototype")
        //ctx.getBean("autowiredParametricBean", "getBean call");

        //check which one is autowired
        //System.out.println("autowiredParametricBean:");
        //autowiredParametricBean.print();

    }

    private void jpaFillDB(){
        System.out.println("DBDataGerenator jpaFillDB start");
        for(int i=0; i<10; i++) {
            Customer cst = new Customer("name" + i, "surname" + i);
            customerService.saveCustomer(cst);
            System.out.println("DBDataGerenator "+cst);
        }
        System.out.println("DBDataGerenator jpaFillDB end");
    }

    private void jdbcFillDB(){
        customerJDBCRepository.runJDBCandFillWithData();
    }
}
