package spring.hello_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerJPARepository repository;

    public Customer findById(long id){
        Optional<Customer> result =  repository.findById(id);
        return result.isPresent()?result.get():null;
    }

    public Collection<Customer> findAll(){
        Collection<Customer> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    public void saveCustomer(Customer customer){
        repository.save(customer);
    }

    public List<Customer> findByLastNameRegex(String lastName){
        return repository.findByLastNameRegex(lastName);
    }

    public List<Customer> findByFirstName(String firstName){
        return repository.findByFirstName(firstName);
    }

    public Customer findCustomerByLastName(String lastName){
        return repository.findCustomerByLastName(lastName);
    }

    public void setCurrentDate(long id, Date currentDate){
        repository.setCurrentDate(id, currentDate);
    }
}
