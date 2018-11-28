package spring.hello_test.service;

import spring.hello_test.model.entity.Customer;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CustomerService {

    public Customer findById(long id);

    public Collection<Customer> findAll();

    public void saveCustomer(Customer customer);

    public List<Customer> findByLastNameRegex(String lastName);

    public List<Customer> findByFirstName(String firstName);

    public Customer findCustomerByLastName(String lastName);

    public void setCurrentDate(long id, Date currentDate);
}
