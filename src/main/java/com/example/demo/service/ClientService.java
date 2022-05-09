package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Person;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.structure.ClientStructure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private PersonRepository personRepository;

    public ClientService(ClientRepository clientRepository, PersonRepository personRepository) {
        super();
        this.personRepository = personRepository;
        this.clientRepository = clientRepository;
    }

    public String saveClient(ClientStructure clientStructure) {
        Person person = new Person();
        person.setNombre(clientStructure.getNombre());
        person.setDireccion(clientStructure.getDireccion());
        person.setTelefono(clientStructure.getTelefono());
        personRepository.save(person);
        Person personSaved = personRepository.findByName(clientStructure.getNombre());
        Client client = new Client();
        client.setPerson(personSaved);
        client.setContrasena(clientStructure.getContrasena());
        client.setEstado(clientStructure.getEstado());
        clientRepository.save(client);
        return "Cliente guardado exitosamente";
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new Error("Client not found with id " + id));
    }

    public Client updateClient(Client client, long id) {

        // we need to check whether client with given id is exist in DB or not
        Client existingClient = clientRepository.findById(id).orElseThrow(() ->
                new Error("Client not found with id " + id));

        existingClient.setContrasena(client.getContrasena());
        existingClient.setEstado(client.getEstado());

        // save existing client to DB
        clientRepository.save(existingClient);
        return existingClient;
    }

    public void deleteClient(long id) {

        // check whether a client exist in a DB or not
        clientRepository.findById(id).orElseThrow(() ->
                new Error("Client not found with id " + id));
        clientRepository.deleteById(id);
    }

}
