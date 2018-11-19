package spring.hello_test.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

public class AutowiredParametricBean {

    private String param;

    public AutowiredParametricBean(String s){
        this.param = s;
    }

    public void print(){
        System.out.println(param);
    }
}
