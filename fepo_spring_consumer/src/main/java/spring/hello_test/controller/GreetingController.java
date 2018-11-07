package spring.hello_test.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spring.hello_test.model.entity.Customer;
import spring.hello_test.model.entity.CustomerList;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/getCustomerFromProvider")
    public Object greeting(@RequestParam(value="id", required = false) Long id)  throws UnsupportedEncodingException {
        if(id != null){
            //ResponseEntity responseEntity = restTemplate.getForEntity("http://localhost:8080/findCustomer?id="+id, Collection.class);

            /*ResponseEntity<Collection> requestResult = restTemplate.exchange("http://localhost:8080/findCustomer?id=" + id, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Collection>() {});

            if(requestResult.getBody() == null || requestResult.getBody().size() == 0)
                return processError("Customer with id = " + id + " is not found");

            for(Object cust : requestResult.getBody()){
                return cust;
            }*/

            return restTemplate.getForEntity("http://localhost:8080/findCustomer?id="+id, Object.class);
        }
        return processError("Id should not be empty");
    }

    @RequestMapping("/getAllCustomersFormProvider")
    public CustomerList getAllCustomers(){
        CustomerList result = restTemplate.getForObject("http://localhost:8080/getAllCustomersAsObject", CustomerList.class);
        return result;
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
