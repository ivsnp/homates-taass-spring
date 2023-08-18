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

    @GetMapping(value = "/")
    public String testAll() {
        return ("<h1>Welcome</h1>");
    }

    @GetMapping(value = "/user")
    public String testUser() {
        return ("<h1>Welcome USER and ADMIN</h1>");
    }

    @GetMapping(value = "/admin")
    public String testAdmin() {
        return ("<h1>Welcome ADMIN</h1>");
    }
}
