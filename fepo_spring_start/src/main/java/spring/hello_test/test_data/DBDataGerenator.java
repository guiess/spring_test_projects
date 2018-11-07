package spring.hello_test.test_data;

import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerRepository;

import java.util.concurrent.Callable;

public class DBDataGerenator implements Callable {

    private CustomerRepository repository;

    public DBDataGerenator(CustomerRepository repository){
        this.repository = repository;
    }

    public Object call(){
        System.out.println("FEPO DBDataGerenator start");
        for(int i=0; i<10; i++) {
            Customer cst = new Customer("name" + i, "surname" + i);
            repository.save(cst);
            System.out.println("FEPO DBDataGerenator "+cst);
        }
        System.out.println("FEPO DBDataGerenator end");
        return null;
    }
}
