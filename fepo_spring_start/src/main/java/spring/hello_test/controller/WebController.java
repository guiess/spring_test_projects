package spring.hello_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Controller
//@EnableWebMvc
public class WebController {

    @Autowired
    CustomerJPARepository repository;

    @RequestMapping(value = "/getAllCustomersView")
    public String getAllCustomersView(Map<String, Object> model){
        System.out.println("FEPO getAllCustomers");
        Collection<Customer> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        model.put("selections", result);
        return "getAllCustomersView";
    }
}
