package com.customer.apicustomer.Controllers;

import com.customer.apicustomer.DTOs.LoginDto;
import com.customer.apicustomer.DTOs.RegisterDto;
import com.customer.apicustomer.DTOs.UserDto;
import com.customer.apicustomer.Services.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "Create a new customer account")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto){
        return accountService.register(registerDto);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "Login with an existing account")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto){
        return accountService.login(loginDto);
    }

}
