package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import com.example.demo.structure.ClientStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        super();
        this.clientService = clientService;
    }

    // build create client REST API
    @PostMapping()
    public ResponseEntity<String> saveClient(@RequestBody ClientStructure clientStructure) {
        return new ResponseEntity<String>(clientService.saveClient(clientStructure), HttpStatus.CREATED);
    }

    // build get all clients REST API
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // build get client by id REST API
    // http://localhost:8080/api/client/1
    @GetMapping("{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long clientId) {
        return new ResponseEntity<Client>(clientService.getClientById(clientId), HttpStatus.OK);
    }

    // build update client REST API
    // http://localhost:8080/api/client/1
    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        return new ResponseEntity<Client>(clientService.updateClient(client, id), HttpStatus.OK);
    }

    // build delete client REST API
    // http://localhost:8080/api/client/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") long id) {

        // delete client from DB
        clientService.deleteClient(id);

        return new ResponseEntity<String>("Client deleted successfully!.", HttpStatus.OK);
    }

}
