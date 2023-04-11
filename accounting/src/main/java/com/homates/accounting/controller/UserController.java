package com.homates.accounting.controller;

import com.homates.accounting.model.User;
import com.homates.accounting.repo.UserRepositoy;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://accounting:4200")
@RestController
@RequestMapping("/api/v1/oauth2")
public class UserController {
    @Autowired
    UserRepositoy repository;

    @GetMapping(value = "/google")
    public User addItem(@RequestBody User user) {
        System.out.println("Test accounting...");
        return user;
    }
}
