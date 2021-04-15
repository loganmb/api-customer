package com.customer.apicustomer.Security;

import com.customer.apicustomer.Interfaces.IPasswordHash;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHash implements IPasswordHash{

    public String encodePassword(String password){

        return BCrypt.hashpw(password, BCrypt.gensalt(10));

    }

    public boolean checkPassword(String password, String hash){

        return BCrypt.checkpw(password, hash);

    }

}