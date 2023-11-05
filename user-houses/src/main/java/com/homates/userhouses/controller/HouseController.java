package com.homates.userhouses.controller;

import com.homates.userhouses.dto.HouseDto;
import com.homates.userhouses.dto.HouseUsersDto;
import com.homates.userhouses.dto.UserDto;
import com.homates.userhouses.model.House;
import com.homates.userhouses.model.UserEntity;
import com.homates.userhouses.repo.HouseRepository;
import com.homates.userhouses.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/user-houses")
public class HouseController {

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/houses/create")
    public ResponseEntity<String> addItem(@RequestBody HouseDto houseDto) {
        System.out.println("Creating a new house...");

        Optional<UserEntity> user = userRepository.findByUsername(houseDto.getUsername_owner());
        if (user.isEmpty())
            return new ResponseEntity<>("Owner not found.", HttpStatus.NOT_FOUND);

        UserEntity owner = user.get();

        House house = new House();
        house.setName(houseDto.getName());
        house.setDescription(houseDto.getDescription());
        house.setAddress(houseDto.getAddress());
        house.setOwner(owner);

        houseRepository.save(house);
        return new ResponseEntity<>("House added.", HttpStatus.OK);
    }

    @GetMapping("/houses/{username}")
    public ResponseEntity<List<HouseUsersDto>> getItems(@PathVariable("username") String username) {
        System.out.println("Getting my houses...");
        ArrayList<HouseUsersDto> houseUsersDto = new ArrayList<>();

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            return new ResponseEntity<>(houseUsersDto, HttpStatus.NOT_FOUND);

        UserEntity userOwner = user.get();
        ArrayList<House> houses = new ArrayList<>(houseRepository.findByOwner(userOwner));
        for (House hu: houses){
            UserEntity owner = hu.getOwner();

            List<UserEntity> roomMates = hu.getRoomMates();
            ArrayList<String> _currentRoomMates = new ArrayList<>();
            for (UserEntity u: roomMates)
                _currentRoomMates.add(u.getUsername());

            HouseUsersDto _currentHouse = new HouseUsersDto();
            _currentHouse.setId(hu.getId());
            _currentHouse.setName(hu.getName());
            _currentHouse.setDescription(hu.getDescription());
            _currentHouse.setAddress(hu.getAddress());
            _currentHouse.setOwner(owner.getUsername());
            _currentHouse.setRoomMates(_currentRoomMates);

            houseUsersDto.add(_currentHouse);
        }
        return new ResponseEntity<>(houseUsersDto, HttpStatus.OK);
    }

    @GetMapping("/houses/rommates/{idHouse}")
    public ResponseEntity<List<String>> getItem(@PathVariable("idHouse") int idHouse) {
        System.out.println("Getting house...");

        Optional<House> house = houseRepository.findById(idHouse);
        if (house.isEmpty())
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);

        House _currentHouse = house.get();

        List<UserEntity> roomMates = _currentHouse.getRoomMates();
        ArrayList<String> _currentRoomMates = new ArrayList<>();
        for (UserEntity u: roomMates)
            _currentRoomMates.add(u.getUsername());
        return new ResponseEntity<>(_currentRoomMates, HttpStatus.OK);
    }

    @PutMapping(value = "/houses/update/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") int id, @RequestBody HouseDto newHouseDto) {
        System.out.println("Updating house "+id+" data...");

        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            House _currentHouse = house.get();
            _currentHouse.setName(newHouseDto.getName());
            _currentHouse.setDescription(newHouseDto.getDescription());
            _currentHouse.setAddress(newHouseDto.getAddress());

            houseRepository.save(_currentHouse);
            return new ResponseEntity<>("House updated.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/houses/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id) {
        System.out.println("Deleting house "+id+" ...");

        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            houseRepository.deleteById(id);
            return new ResponseEntity<>("House has been deleted.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/houses/add-roommate/{id}/{username}")
    public ResponseEntity<String> addRoomMate(@PathVariable("id") int idHouse,
                                              @PathVariable("username") String username) {
        System.out.println("Adding "+username+", a new room mate to the house "+idHouse+" ...");

        Optional<House> house = houseRepository.findById(idHouse);
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (house.isPresent() && userEntity.isPresent()) {
            House _currentHouse = house.get();
            UserEntity _currentUserEntity = userEntity.get();

            if (_currentHouse.getRoomMates().contains(_currentUserEntity))
                return new ResponseEntity<>("User already assigned to the house.", HttpStatus.BAD_REQUEST);

            _currentHouse.getRoomMates().add(userEntity.get());
            houseRepository.save(_currentHouse);

            return new ResponseEntity<>("User has been added to the house.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House or user not found.", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping(value = "/houses/remove-roommate/{id}/{username}")
    public ResponseEntity<String> removeRoomMate(@PathVariable("id") int idHouse,
                                                 @PathVariable("username") String username) {
        System.out.println("Removing "+username+", a new room mate to the house "+idHouse+" ...");

        Optional<House> house = houseRepository.findById(idHouse);
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (house.isPresent() && userEntity.isPresent()) {
            House _currentHouse = house.get();
            UserEntity _currentUserEntity = userEntity.get();

            if (! _currentHouse.getRoomMates().contains(_currentUserEntity))
                return new ResponseEntity<>("User not assigned to the house.", HttpStatus.BAD_REQUEST);

            _currentHouse.getRoomMates().remove(userEntity.get());
            houseRepository.save(_currentHouse);

            return new ResponseEntity<>("User has been removed from the house.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("House or user not found.", HttpStatus.NOT_FOUND);
    }

}
