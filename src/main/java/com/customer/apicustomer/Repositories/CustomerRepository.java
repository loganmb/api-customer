package com.customer.apicustomer.Repositories;

import com.customer.apicustomer.Entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.userName LIKE %:username%")
    public List<Customer> findByUserName(@Param("username") String username);

}
