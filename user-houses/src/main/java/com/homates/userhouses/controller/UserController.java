package com.homates.userhouses.controller;

import com.homates.userhouses.dto.UserDto;
import com.homates.userhouses.model.UserEntity;
import com.homates.userhouses.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user-houses")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
    public ResponseEntity<String> addItem(@RequestBody UserDto userDto){
        if(userRepository.existsByUsername(userDto.getUsername()))
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());

        userRepository.save(user);
        return new ResponseEntity<>("User created with success.", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getItems() {
        System.out.println("Getting all users...");
        ArrayList<UserEntity> users = new ArrayList<>(userRepository.findAll());
        ArrayList<UserDto> usersDto = new ArrayList<>();
        for (UserEntity u: users) {
            UserDto _currentUser = new UserDto();
            _currentUser.setUsername(u.getUsername());
            _currentUser.setName(u.getName());
            _currentUser.setSurname(u.getSurname());
            _currentUser.setEmail(u.getEmail());
            _currentUser.setBio(u.getBio());
            usersDto.add(_currentUser);
        }
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getItem(@PathVariable("username") String username) {
        System.out.println("Getting user...");

        Optional<UserEntity> user = userRepository.findByUsername(username);
        UserDto _currentUser = new UserDto();

        if (user.isEmpty()) {
            return new ResponseEntity<>(_currentUser, HttpStatus.NOT_FOUND);
        }

        UserEntity u = user.get();
        _currentUser.setUsername(u.getUsername());
        _currentUser.setName(u.getName());
        _currentUser.setSurname(u.getSurname());
        _currentUser.setEmail(u.getEmail());
        _currentUser.setBio(u.getBio());
        return new ResponseEntity<>(_currentUser, HttpStatus.OK);
    }

    @PutMapping(value = "/user/update/{username}")
    public ResponseEntity<String> updateItem(@PathVariable("username") String username,
                                             @RequestBody UserDto newUserDto) {
        System.out.println("Updating user "+username+" data...");

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity _currentUser = user.get();
            _currentUser.setUsername(newUserDto.getUsername());
            _currentUser.setName(newUserDto.getName());
            _currentUser.setSurname(newUserDto.getSurname());
            _currentUser.setEmail(newUserDto.getEmail());
            _currentUser.setBio(newUserDto.getBio());

            userRepository.save(_currentUser);
            return new ResponseEntity<>("User updated.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/user/delete/{username}")
    public ResponseEntity<String> deleteItem(@PathVariable("username") String username) {
        System.out.println("Deleting user "+username+" ...");

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return new ResponseEntity<>("User has been deleted.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }
}
