package com.customer.apicustomer.Controllers;

import com.customer.apicustomer.DTOs.RegisterDto;
import com.customer.apicustomer.DTOs.UserDto;
import com.customer.apicustomer.Services.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    @ApiOperation(value = "Create a new customer account")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto){
        return customerService.register(registerDto);
    }

}
