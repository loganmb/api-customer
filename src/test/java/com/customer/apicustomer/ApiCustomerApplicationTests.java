package com.customer.apicustomer;

import com.customer.apicustomer.Entities.Address;
import com.customer.apicustomer.Entities.Customer;
import com.customer.apicustomer.Repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApiCustomerApplicationTests {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreated() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Test");
        customer.setCpf("12345678912");
        customer.setUserName("usertest");
        customer.setPasswordHash("arfhug-qethuq-etjh");
        customer.setPasswordSalt("jar-0gaergn-q9regh");

        Address address = new Address();

        address.setStreet("test st.");
        address.setNumber("1");
        address.setDistrict("a test district");
        address.setState("São Paulo");
        address.setCountry("Brazil");
        address.setPostalCode("0800-000");
        address.setComplement("nothing to add");

        List<Address> addresses = new ArrayList<>();

        addresses.add(address);

        customer.setAddressList(addresses);
        customerRepository.save(customer);
    }

    @Test
    public void testRead() {
        Customer customer = customerRepository.findById(1).get();
        assertNotNull(customer);
        assertEquals("Test", customer.getName());
    }


    @Test
    public void testUpdate() {
        Customer customer = customerRepository.findById(1).get();
        customer.setUserName("updateTest");
        customerRepository.save(customer);
    }

    @Test
    public void testDelete() {

        if (customerRepository.existsById(1)) {
            customerRepository.deleteById(1);
        }

    }


}
