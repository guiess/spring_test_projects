package spring.hello_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.hello_test.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerJPARepository extends JpaRepository<Customer, Long> {
    public List<Customer> findByLastName(String lastName);

    @Query("from Customer where lastName like concat('%', :lastName, '%')")
    public List<Customer> findByLastNameRegex(@Param("lastName") String lastName);

    public List<Customer> findByFirstName(String firstName);

    public Customer findCustomerByLastName(String lastName);

    @Transactional
    @Modifying
    @Query("update Customer set currentDate = :currentDate where id = :id")
    public void setCurrentDate(@Param("id") long id, @Param("currentDate") Date currentDate);
}
