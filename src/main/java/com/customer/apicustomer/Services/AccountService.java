package com.customer.apicustomer.Services;

import com.customer.apicustomer.DTOs.AddressDto;
import com.customer.apicustomer.DTOs.LoginDto;
import com.customer.apicustomer.DTOs.RegisterDto;
import com.customer.apicustomer.DTOs.UserDto;
import com.customer.apicustomer.Entities.Address;
import com.customer.apicustomer.Entities.Customer;
import com.customer.apicustomer.Interfaces.IPasswordHash;
import com.customer.apicustomer.Interfaces.ITokenService;
import com.customer.apicustomer.Repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ITokenService tokenService;

    private HttpHeaders headers;

    @Autowired
    private IPasswordHash passwordHash;

    public AccountService(CustomerRepository customerRepository, ITokenService tokenService) {
        this.customerRepository = customerRepository;
        this.tokenService = tokenService;
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
    }

    @Transactional
    public ResponseEntity<UserDto> register(RegisterDto registerDto) {

        // try {
        if (userExists(registerDto.getUserName())) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\":\"Username already exists\"}";
            return new ResponseEntity(body, headers, HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer();
        customer.setUserName(registerDto.getUserName());
        customer.setName(registerDto.getName());
        customer.setCpf(registerDto.getCpf());
        customer.setPasswordHash(passwordHash.encodePassword(registerDto.getPassword()));

        List<Address> addresses = new ArrayList<>();

        if (!registerDto.getAddressList().isEmpty()) {
            for (AddressDto a : registerDto.getAddressList()) {
                Address address = new Address();
                address.setStreet(a.getStreet());
                address.setNumber(a.getNumber());
                address.setDistrict(a.getDistrict());
                address.setState(a.getState());
                address.setCountry(a.getCountry());
                address.setPostalCode(a.getPostalCode());
                address.setComplement(a.getComplement());
                addresses.add(address);
            }
        }

        customer.setAddressList(addresses);

        customerRepository.save(customer);

        UserDto userDto = new UserDto();
        userDto.setUserName(customer.getUserName());
        userDto.setToken(tokenService.createToken(customer));

        return new ResponseEntity(userDto, headers, HttpStatus.CREATED);
        // }
        // catch(Exception e)
        // {
        //     headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        //     String body = "{\"message\": \"Internal Error\"}" + "\n" + "\"Error:\"" + "\"" + e.getMessage() + "\"";
        //     return new ResponseEntity(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        // }

    }


    @Transactional
    public ResponseEntity<UserDto> login(LoginDto loginDto) {
        Customer customer = new Customer();

        List<Customer> customers = customerRepository.findByUserName(loginDto.getUserName());

        if (customers == null || customers.size() < 1)
        {
            String body = "{\"message\":\"Invalid Username/Password\"}";
            return new ResponseEntity(body, headers, HttpStatus.UNAUTHORIZED);
        }

        for (Customer c : customers) {
            if(c.getUserName().equals(loginDto.getUserName())) {
                customer = c;
                break;
            }
        }

        UserDto userDto = new UserDto();
        userDto.setUserName(customer.getUserName());
        userDto.setToken(tokenService.createToken(customer));

        return new ResponseEntity(userDto, headers, HttpStatus.OK);
    }


    private boolean userExists(String userName) {

        List<Customer> customers = customerRepository.findByUserName(userName);

        if (customers == null || customers.size() < 1)
        {
            return false;
        }
        return true;
//        if (customerRepository.findByUserName(userName) == null || customerRepository.findByUserName(userName).getUserName().isEmpty())
//            return true;
//
//        return false;

    }


}
