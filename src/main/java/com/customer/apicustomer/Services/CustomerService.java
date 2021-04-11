package com.customer.apicustomer.Services;

import com.customer.apicustomer.DTOs.AddressDto;
import com.customer.apicustomer.DTOs.LoginDto;
import com.customer.apicustomer.DTOs.RegisterDto;
import com.customer.apicustomer.DTOs.UserDto;
import com.customer.apicustomer.Entities.Address;
import com.customer.apicustomer.Entities.Customer;
import com.customer.apicustomer.Interfaces.ITokenService;
import com.customer.apicustomer.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.resources.cldr.da.CalendarData_da_DK;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ITokenService tokenService;

    private HttpHeaders headers;


    public CustomerService(CustomerRepository customerRepository, ITokenService tokenService) {
        this.customerRepository = customerRepository;
        this.tokenService = tokenService;
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
    }

    @Transactional
    public ResponseEntity<UserDto> register(RegisterDto registerDto){

        try {
            if (userExists(registerDto.getUserName())) {
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
                String body = "{\"message\":\"Username already exists\"}";
                return new ResponseEntity(body, headers, HttpStatus.BAD_REQUEST);
            }

            Customer customer = new Customer();
            customer.setUserName(registerDto.getUserName());
            customer.setName(registerDto.getName());
            customer.setCpf(registerDto.getCpf());

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
        }
        catch(Exception e)
        {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            String body = "{\"message\": \"Internal Error\"}" + "\n" + "\"Error:\"" + "\"" + e.getMessage() + "\"";
            return new ResponseEntity(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Transactional
    public ResponseEntity<LoginDto> login(LoginDto loginDto)
    {
        return null;
    }



    private boolean userExists(String userName) {

        return (customerRepository.findByUserName(userName).size()) > 1 ? true : false;

    }


}
