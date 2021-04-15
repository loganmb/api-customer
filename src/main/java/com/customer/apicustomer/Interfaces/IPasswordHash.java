package com.customer.apicustomer.Interfaces;

public interface IPasswordHash {
    
    String encodePassword(String password);

    boolean checkPassword(String password, String hash);

}
