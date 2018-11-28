package spring.hello_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.hello_test.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.model.entity.CustomerList;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static String message_template = "Hello, %s";
    private static AtomicLong counter = new AtomicLong();

    @Autowired
    CustomerService customerService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", required = false, defaultValue = "Guest") String name,
                            @RequestParam(value="id", required = false) Long id){
        System.out.println("FEPO repository "+customerService);

        if(id != null){
            Customer cust = customerService.findById(id);

            if (cust != null)
                return new Greeting(id,
                        String.format(message_template, cust.getFirstName() + ' ' + cust.getLastName() +' ' + cust.getRecordCurrentDate()));

        }
        return new Greeting(counter.incrementAndGet(),
                String.format(message_template, name));
    }

    @RequestMapping("/getAllCustomers")
    public Collection<Customer> getAllCustomers(){
        System.out.println("FEPO getAllCustomers");
        return customerService.findAll();
    }

    @RequestMapping("/getAllCustomersAsObject")
    public CustomerList getAllCustomersAsObject(){
        Collection<Customer> result = new ArrayList<>();
        customerService.findAll().forEach(result::add);
        return new CustomerList(result);
    }

    @RequestMapping("/findCustomer")
    public /*Collection<Customer>*/Object findCustomer(@RequestParam(value="name", required = false) String firstName,
                                             @RequestParam(value="lastName", required = false) String lastName,
                                             @RequestParam(value="id", required = false) Long id) throws Exception{
        System.out.println("FEPO findCustomer");
        if(id == null && firstName == null && lastName == null) return processError("Please indicate any parameter value");

        if(id!= null){
            Customer customer = customerService.findById(id);
            return customer==null? Collections.EMPTY_LIST: Collections.singletonList(customer);
        }

        if(firstName!= null){
            return customerService.findByFirstName(firstName);
        }

        return customerService.findByLastNameRegex(lastName);
    }

    @RequestMapping("/findCustomerById")
    public Customer findCustomer(@RequestParam(value="id", required = true) Long id) throws Exception{
        System.out.println("FEPO findCustomerById");

        return customerService.findById(id);
    }


    @RequestMapping("/addCustomer")
    public ResponseEntity addCustomer(@RequestParam(value="name", required = true) String firstName,
                                      @RequestParam(value="lastName", required = true) String lastName){
        Customer newCustomer = new Customer(firstName, lastName);
        customerService.saveCustomer(newCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/successResult"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping("/successResult")
    public String successResult(){
        return "operation have been performed successfuly";
    }

    public ResponseEntity processError(String errorText) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/errorResult?errorText="+ URLEncoder.encode(errorText,"UTF-8")));
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/errorResult")
    public String errorResult(@RequestParam(value="errorText", required = false, defaultValue = "Error happened") String errorText)
            throws UnsupportedEncodingException{
        return URLDecoder.decode(errorText,"UTF-8");
    }


}
