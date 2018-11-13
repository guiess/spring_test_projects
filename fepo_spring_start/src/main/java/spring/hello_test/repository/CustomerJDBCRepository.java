package spring.hello_test.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import spring.hello_test.model.entity.Customer;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerJDBCRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void runJDBCandFillWithData(){
        System.out.println("runJDBCandFillWithData");

        jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers (id serial, firstname varchar2, lastname varchar2)");

        List<Object[]> namesList = Arrays.asList("John Dou","Karl Sagan","Ray Bradburry","Jerald Darrell").stream()
                .map(name -> name.split(" ")).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("insert into customers(firstname, lastname) values(?, ?)", namesList);

        jdbcTemplate.query("select id, firstname, lastname from customers",
                (rs, rownum) -> new Customer(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname")))
            .stream().forEach(customer -> System.out.println(customer.toString()));

        jdbcTemplate.query("select * from customer",
                (rs, rownum) -> new Customer(rs.getLong(1), rs.getString(3), rs.getString(4)))
                .stream().forEach(customer -> System.out.println(customer.toString()));

    }


}
