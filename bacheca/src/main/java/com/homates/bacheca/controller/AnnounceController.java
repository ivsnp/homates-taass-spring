package com.homates.bacheca.controller;

import com.homates.bacheca.model.Announce;
import com.homates.bacheca.model.Document;
import com.homates.bacheca.repo.AnnounceRepository;
import com.homates.bacheca.repo.DocumentRepository;
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
    @Autowired
    DocumentRepository documentRepository;
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
            _currentAnnounce.setDate((newAnnounce.getDate()));
            _currentAnnounce.setUser((newAnnounce.getUser()));
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


    @PutMapping(value = "/announces/add-document/{id}")
    public ResponseEntity<Announce> addDocument(@PathVariable("id") long id, @RequestBody Document document) {
        System.out.println("Add Document in Announce with ID = " + id + "...");
        Optional<Announce> announce = repository.findById(id);
        document = documentRepository.save(document);
        if (announce.isPresent()) {
            Announce _announce = announce.get();
            _announce.addDocument(document);
            repository.save(_announce);
            return new ResponseEntity<>(_announce,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/announces/delete-document/{id}/{id_doc}")
    public ResponseEntity<Announce> deleteDocument(@PathVariable("id") long id, @PathVariable("id_doc") long id_doc) {
        System.out.println("Deleting Document in Announce with ID = " + id + "...");
        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            Announce _announce = announce.get();
            Document document = _announce.findDocumentByID(id_doc);
            _announce.removeDocument(document);
            return new ResponseEntity<>(repository.save(_announce), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/announces/update-document/{id}/{id_doc}")
    public ResponseEntity<Announce> updateDocument(@PathVariable("id") long id,@PathVariable("id_doc") long id_doc, @RequestBody Document document) {
        System.out.println("Updating document...");

        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            Announce _currentAnnounce = announce.get();
            Document newDoc = _currentAnnounce.findDocumentByID(id_doc);
            newDoc.setName(document.getName());
            _currentAnnounce.addDocument(newDoc);
            repository.save(_currentAnnounce);
            return new ResponseEntity<>(_currentAnnounce, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}