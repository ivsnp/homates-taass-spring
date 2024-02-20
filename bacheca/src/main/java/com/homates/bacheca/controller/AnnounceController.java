package com.homates.bacheca.controller;

import com.homates.bacheca.dto.AnnounceDto;
import com.homates.bacheca.dto.AnnounceDtoEdit;
import com.homates.bacheca.model.Announce;
import com.homates.bacheca.model.Document;
import com.homates.bacheca.repo.AnnounceRepository;
import com.homates.bacheca.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/bacheca")
public class AnnounceController {


    @Autowired
    AnnounceRepository repository;
    @Autowired
    DocumentRepository documentRepository;



    //add announce
    @PostMapping(value = "/announces/create")
    public ResponseEntity<String> addAnnounce(@RequestBody AnnounceDto announceDto) {
        System.out.println("Creating new announce...");

        Announce announce = new Announce();
        announce.setDate(announceDto.getDate());
        announce.setDescription(announceDto.getDescription());
        announce.setUser(announceDto.getUser());
        announce.setIdHouse(announceDto.getIdHouse());
        announce.setDocuments(new ArrayList<>());

        repository.save(announce);
        return new ResponseEntity<>("Announcement added.", HttpStatus.OK);
    }

    @GetMapping("/announces/house/{idHouse}")
    public ResponseEntity<List<Announce>> getAnnounce(@PathVariable("idHouse") int idHouse) {
        System.out.println("Get all announces...");
        List<Announce> announceList = repository.findByIdHouse(idHouse);
        return new ResponseEntity<>(announceList, HttpStatus.OK);
    }

    @PutMapping(value = "/announces/update")
    public ResponseEntity<Announce> updateAnnounce( @RequestBody AnnounceDtoEdit announceDto) {
        System.out.println("Updating announce...");

        Optional<Announce> announce = repository.findById(announceDto.getIdAnnounce());
        if (announce.isPresent()) {
            Announce _currentAnnounce = announce.get();
            _currentAnnounce.setDescription(announceDto.getDescription());
            _currentAnnounce.setDocuments(new ArrayList<>());
            return new ResponseEntity<>(repository.save(_currentAnnounce), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/announces/delete/{id}")
    public ResponseEntity<String> deleteAnnounce(@PathVariable("id") int id) {
        System.out.println("Deleting announce...");

        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Announce has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /*@PutMapping(value = "/announces/add-document/{id}")
    public ResponseEntity<Announce> addDocument(@PathVariable("id") int id, @RequestBody Document document) {
        System.out.println("Add Document in Announce with ID = " + id + "...");
        Optional<Announce> announce = repository.findById(id);
        document = documentRepository.save(document);
        if (announce.isPresent()) {
            Announce _announce = announce.get();
            _announce.getDocuments().add(document);
            repository.save(_announce);
            return new ResponseEntity<>(_announce,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/announces/delete-document/{id}/{id_doc}")
    public ResponseEntity<Announce> deleteDocument(@PathVariable("id") int id, @PathVariable("id_doc") int id_doc) {
        System.out.println("Deleting Document in Announce with ID = " + id + "...");
        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            Announce _announce = announce.get();

            Optional<Document> documentOptional = documentRepository.findById(id_doc);
            if (documentOptional.isEmpty())
                return new ResponseEntity<>(new Announce(), HttpStatus.NOT_FOUND);

            Document _currentDocument = documentOptional.get();

            _announce.getDocuments().remove(_currentDocument);
            return new ResponseEntity<>(repository.save(_announce), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/announces/update-document/{id}/{id_doc}")
    public ResponseEntity<Announce> updateDocument(@PathVariable("id") int id,@PathVariable("id_doc") int id_doc, @RequestBody Document document) {
        System.out.println("Updating document...");

        Optional<Announce> announce = repository.findById(id);
        if (announce.isPresent()) {
            Announce _currentAnnounce = announce.get();

            Optional<Document> documentOptional = documentRepository.findById(id_doc);
            if (documentOptional.isEmpty())
                return new ResponseEntity<>(new Announce(), HttpStatus.NOT_FOUND);

            Document _currentDocument = documentOptional.get();
            _currentDocument.setCategory(document.getCategory());

            _currentAnnounce.getDocuments().add(_currentDocument);
            repository.save(_currentAnnounce);
            return new ResponseEntity<>(_currentAnnounce, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}