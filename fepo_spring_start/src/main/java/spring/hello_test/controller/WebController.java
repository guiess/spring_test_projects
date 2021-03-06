package spring.hello_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import spring.hello_test.application.AutowiredParametricBean;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.repository.CustomerJPARepository;
import spring.hello_test.service.CustomerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Controller
//@EnableWebMvc
public class WebController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/getAllCustomersView")
    public String getAllCustomersView(Map<String, Object> model){
        System.out.println("FEPO getAllCustomers");
        model.put("selections", customerService.findAll());
        return "getAllCustomersView";
    }
}
