package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        super();
        this.personService = personService;
    }

    // build create person REST API
    @PostMapping()
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return new ResponseEntity<Person>(personService.savePerson(person), HttpStatus.CREATED);
    }

    // build get all persons REST API
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    // build get person by id REST API
    // http://localhost:8080/api/person/1
    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") long personId) {
        return new ResponseEntity<Person>(personService.getPersonById(personId), HttpStatus.OK);
    }

    // build update person REST API
    // http://localhost:8080/api/person/1
    @PutMapping("{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
        return new ResponseEntity<Person>(personService.updatePerson(person, id), HttpStatus.OK);
    }

    // build delete person REST API
    // http://localhost:8080/api/person/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") long id) {

        // delete person from DB
        personService.deletePerson(id);

        return new ResponseEntity<String>("Person deleted successfully!.", HttpStatus.OK);
    }

}
