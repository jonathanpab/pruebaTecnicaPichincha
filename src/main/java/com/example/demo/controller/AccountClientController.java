package com.example.demo.controller;

import com.example.demo.entity.AccountClient;
import com.example.demo.service.AccountClientService;
import com.example.demo.structure.AccountStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class AccountClientController {

    private AccountClientService accountClientService;

    public AccountClientController(AccountClientService accountClientService) {
        super();
        this.accountClientService = accountClientService;
    }

    // build create account REST API
    @PostMapping()
    public ResponseEntity<String> saveAccountClient(@RequestBody AccountStructure accountStructure) {
        return new ResponseEntity<String>(accountClientService.saveAccountClient(accountStructure), HttpStatus.CREATED);
    }

    // build get all account REST API
    @GetMapping
    public List<AccountClient> getAllAccountClients() {
        return accountClientService.getAllAccountClients();
    }

    // build get account by id REST API
    // http://localhost:8080/api/account-client/1
    @GetMapping("{id}")
    public ResponseEntity<AccountClient> getAccountClientById(@PathVariable("id") long accountClientId) {
        return new ResponseEntity<AccountClient>(accountClientService.getAccountClientById(accountClientId), HttpStatus.OK);
    }

    // build update account REST API
    // http://localhost:8080/api/account-client/1
    @PutMapping("{id}")
    public ResponseEntity<AccountClient> updateAccountClient(@PathVariable("id") long id, @RequestBody AccountClient accountClient) {
        return new ResponseEntity<AccountClient>(accountClientService.updateAccountClient(accountClient, id), HttpStatus.OK);
    }

    // build delete account REST API
    // http://localhost:8080/api/account-client/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccountClient(@PathVariable("id") long id) {

        // delete client from DB
        accountClientService.deleteAccountClient(id);

        return new ResponseEntity<String>("Account deleted successfully!.", HttpStatus.OK);
    }

}
