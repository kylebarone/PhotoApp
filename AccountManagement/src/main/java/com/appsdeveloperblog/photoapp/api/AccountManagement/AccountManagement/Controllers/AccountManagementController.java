package com.appsdeveloperblog.photoapp.api.AccountManagement.AccountManagement.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userManagement")
public class AccountManagementController {

    @GetMapping
    public String test() {
        return "up and running";
    }

}
