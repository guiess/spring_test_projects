package spring.hello_test.model.entity;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerList {

    private Collection<Customer> customers;

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    public CustomerList() {
        this.customers = new ArrayList<>();
    }

    public CustomerList(Collection<Customer> customers) {
        this.customers = customers;
    }

}
