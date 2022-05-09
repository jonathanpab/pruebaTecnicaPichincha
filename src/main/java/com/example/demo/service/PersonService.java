package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        super();
        this.personRepository = personRepository;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(long id) {
        return personRepository.findById(id).orElseThrow(() ->
                new Error("Person not found with id " + id));
    }

    public Person getPersonByName(String name) {
        Person person = personRepository.findByName(name);
        return person;
    }

    public Person updatePerson(Person person, long id) {

        // we need to check whether person with given id is exist in DB or not
        Person existingPerson = personRepository.findById(id).orElseThrow(() ->
                new Error("Person not found with id " + id));

        existingPerson.setDireccion(person.getDireccion());
        existingPerson.setEdad(person.getEdad());
        existingPerson.setGenero(person.getGenero());
        existingPerson.setIdentificacion(person.getIdentificacion());
        existingPerson.setTelefono(person.getTelefono());
        existingPerson.setNombre(person.getNombre());
        // save existing person to DB
        personRepository.save(existingPerson);
        return existingPerson;
    }

    public void deletePerson(long id) {

        // check whether a person exist in a DB or not
        personRepository.findById(id).orElseThrow(() ->
                new Error("Person not found with id " + id));
        personRepository.deleteById(id);
    }

}
