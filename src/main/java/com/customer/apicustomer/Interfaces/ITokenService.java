package com.customer.apicustomer.Interfaces;

import com.customer.apicustomer.Entities.Customer;

public interface ITokenService {

    String createToken(Customer customer);

}
