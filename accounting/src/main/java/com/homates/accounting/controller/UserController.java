package com.homates.accounting.controller;

import com.homates.accounting.model.User;
import com.homates.accounting.repo.UserRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://accounting:4200")
@RestController
@RequestMapping("/api/v1/accounting")
public class UserController {
    @Autowired
    UserRepositoy repository;

    @GetMapping(value = "/test")
    public User addItem(@RequestBody User user) {
        System.out.println("Test accounting...");
        return user;
    }
}
