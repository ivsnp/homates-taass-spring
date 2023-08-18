package com.homates.bacheca.controller;

import com.homates.bacheca.model.Announce;
import com.homates.bacheca.repo.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://bacheca:4200")
@RestController
@RequestMapping("/api/v1/bacheca")
public class AnnounceController {
    @Autowired
    AnnounceRepository repository;

    //add announce
    @PostMapping(value = "/announces/create")
    public Announce addItem(@RequestBody Announce announce) {
        System.out.println("Creating new announce...");
        return repository.save(announce);
    }

    @GetMapping("/announces")
    public List<Announce> getItems() {
        System.out.println("Get all announces...");
        List<Announce> announceList = new ArrayList<>();
        repository.findAll().forEach(announceList::add);
        return announceList;
    }

    @PutMapping(value = "/announces/update/{id}")
    public ResponseEntity<Announce> updateItem(@PathVariable("id") long id, @RequestBody Announce newAnnounce) {
        System.out.println("Updating announce...");

        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            Announce _currentAnnounce = announce.get();
            _currentAnnounce.setDescription(newAnnounce.getDescription());
            return new ResponseEntity<>(repository.save(_currentAnnounce), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/announces/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id) {
        System.out.println("Deleting announce...");

        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Announce has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
