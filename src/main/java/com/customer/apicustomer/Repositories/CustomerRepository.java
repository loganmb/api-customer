package com.customer.apicustomer.Repositories;


import com.customer.apicustomer.Entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT s FROM Customers s WHERE s.name LIKE %:name%")
    public List<Customer> findByName(@Param("name") String name);

}
