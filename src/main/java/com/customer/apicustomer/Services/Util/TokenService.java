package com.customer.apicustomer.Services.Util;

import com.customer.apicustomer.Entities.Customer;
import com.customer.apicustomer.Interfaces.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TokenService implements ITokenService {

    /*
        TokenService generates a token for user session
     */

    private final SecretKey CHAVE = Keys.hmacShaKeyFor(
            "7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6"
                    .getBytes(StandardCharsets.UTF_8));


    @Override
    public String createToken(Customer customer) {
         String jwtToken = Jwts.builder()
                .setSubject(customer.getUserName())
                .setIssuer("localhost:8080")
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusMinutes(15L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()))
                .signWith(CHAVE, SignatureAlgorithm.HS512)
                .compact();

         return jwtToken;
    }



}
