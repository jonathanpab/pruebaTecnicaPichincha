package com.example.demo.service;

import com.example.demo.entity.AccountClient;
import com.example.demo.entity.Client;
import com.example.demo.repository.AccountClientRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.structure.AccountStructure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountClientService {

    private AccountClientRepository accountClientRepository;
    private ClientRepository clientRepository;

    public AccountClientService(AccountClientRepository accountClientRepository, ClientRepository clientRepository) {
        super();
        this.accountClientRepository = accountClientRepository;
        this.clientRepository = clientRepository;
    }

    public String saveAccountClient(AccountStructure accountStructure) {
        Client client = clientRepository.findByName(accountStructure.getCliente());
        AccountClient accountClient = new AccountClient();
        accountClient.setNumero_cuenta(accountStructure.getNumero_cuenta());
        accountClient.setTipo(accountStructure.getTipo());
        accountClient.setSaldo_inicial(accountStructure.getSaldo_inicial());
        accountClient.setEstado(accountStructure.getEstado());
        accountClient.setClient(client);
        accountClientRepository.save(accountClient);
        return "Cuenta guardada exitosamente";
    }

    public List<AccountClient> getAllAccountClients() {
        return accountClientRepository.findAll();
    }

    public AccountClient getAccountClientById(long id) {
        return accountClientRepository.findById(id).orElseThrow(() ->
                new Error("AccountClient not found with id " + id));
    }

    public AccountClient updateAccountClient(AccountClient accountClient, long id) {

        // we need to check whether accountClient with given id is exist in DB or not
        AccountClient existingAccountClient = accountClientRepository.findById(id).orElseThrow(() ->
                new Error("AccountClient not found with id " + id));

        existingAccountClient.setTipo(accountClient.getTipo());
        existingAccountClient.setSaldo_inicial(accountClient.getSaldo_inicial());
        existingAccountClient.setEstado(accountClient.getEstado());
        existingAccountClient.setEstado(accountClient.getEstado());

        // save existing accountClient to DB
        accountClientRepository.save(existingAccountClient);
        return existingAccountClient;
    }

    public void deleteAccountClient(long id) {

        // check whether a accountClient exist in a DB or not
        accountClientRepository.findById(id).orElseThrow(() ->
                new Error("AccountClient not found with id " + id));
        accountClientRepository.deleteById(id);
    }

}
