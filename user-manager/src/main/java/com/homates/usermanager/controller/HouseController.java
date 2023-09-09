package com.homates.usermanager.controller;

import com.homates.usermanager.dto.HouseDto;
import com.homates.usermanager.model.House;
import com.homates.usermanager.model.UserEntity;
import com.homates.usermanager.repository.HouseRepository;
import com.homates.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://user-manager:4200")
@RestController
@RequestMapping("/api/v1/user")
public class HouseController {

    @Autowired
    HouseRepository repositoryHouses;

    @Autowired
    UserRepository repositoryUsers;

    @PostMapping(value = "/houses/create")
    public ResponseEntity<String> addItem(@RequestBody HouseDto houseDto) {
        // TODO: set owner
        System.out.println("Creating a new house...");

        House house = new House();
        house.setName(houseDto.getName());
        house.setDescription(houseDto.getDescription());
        house.setAddress(houseDto.getAddress());
        repositoryHouses.save(house);
        return new ResponseEntity<>("House added.", HttpStatus.OK);
    }

    @GetMapping("/houses")
    public ResponseEntity<List<House>> getItems() {
        // TODO: fix using returning only users' houses
        // TODO: do not return users data
        System.out.println("Get my houses...");
        ArrayList<House> houses = new ArrayList<>(repositoryHouses.findAll());
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PutMapping(value = "/houses/update/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") int id, @RequestBody HouseDto newHouseDto) {
        System.out.println("Updating house "+id+" data...");

        Optional<House> house = repositoryHouses.findById(id);
        if (house.isPresent()) {
            House _currentHouse = house.get();
            _currentHouse.setName(newHouseDto.getName());
            _currentHouse.setDescription(newHouseDto.getDescription());
            _currentHouse.setAddress(newHouseDto.getAddress());

            repositoryHouses.save(_currentHouse);

            return new ResponseEntity<>("House updated.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/houses/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id) {
        System.out.println("Deleting house "+id+" ...");

        Optional<House> house = repositoryHouses.findById(id);
        if (house.isPresent()) {
            repositoryHouses.deleteById(id);
            return new ResponseEntity<>("House has been deleted.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/houses/add-roommate/{id}/{username}")
    public ResponseEntity<String> addRoomMate(@PathVariable("id") int idHouse,
                                              @PathVariable("username") String username) {
        System.out.println("Adding "+username+", a new room mate to the house "+idHouse+" ...");

        Optional<House> house = repositoryHouses.findById(idHouse);
        Optional<UserEntity> userEntity = repositoryUsers.findByUsername(username);
        if (house.isPresent() && userEntity.isPresent()) {
            House _currentHouse = house.get();
            UserEntity _currentUserEntity = userEntity.get();

            if (_currentHouse.getUsers().contains(_currentUserEntity))
                return new ResponseEntity<>("User already assigned to the house.", HttpStatus.BAD_REQUEST);

            _currentHouse.getUsers().add(userEntity.get());

            repositoryHouses.save(_currentHouse);

            return new ResponseEntity<>("User has been added to the house.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House or user not found.", HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/houses/remove-roommate/{id}/{username}")
    public ResponseEntity<String> removeRoomMate(@PathVariable("id") int idHouse,
                                                 @PathVariable("username") String username) {
        System.out.println("Removing "+username+", a new room mate to the house "+idHouse+" ...");

        Optional<House> house = repositoryHouses.findById(idHouse);
        Optional<UserEntity> userEntity = repositoryUsers.findByUsername(username);
        if (house.isPresent() && userEntity.isPresent()) {
            House _currentHouse = house.get();
            UserEntity _currentUserEntity = userEntity.get();

            if (! _currentHouse.getUsers().contains(_currentUserEntity))
                return new ResponseEntity<>("User not assigned to the house.", HttpStatus.BAD_REQUEST);

            _currentHouse.getUsers().remove(userEntity.get());

            repositoryHouses.save(_currentHouse);

            return new ResponseEntity<>("User has been removed from the house.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House or user not found.", HttpStatus.NOT_FOUND);
    }

}
