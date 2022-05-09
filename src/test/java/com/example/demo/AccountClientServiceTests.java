package com.example.demo;

import com.example.demo.entity.AccountClient;
import com.example.demo.repository.AccountClientRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.AccountClientService;
import com.example.demo.structure.AccountStructure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AccountClientServiceTests {
    @InjectMocks
    AccountClientService accountClientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AccountClientRepository accountClientRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccountClientTest() {
        AccountStructure accountStructure = new AccountStructure();
        accountStructure.setNumero_cuenta(478758);
        accountStructure.setTipo("Ahorro");
        accountStructure.setSaldo_inicial(2000.00f);
        accountStructure.setEstado(true);
        accountStructure.setCliente("Jose Lema");

        AccountClient accountClient = new AccountClient();
        accountClient.setNumero_cuenta(accountStructure.getNumero_cuenta());
        accountClient.setTipo(accountStructure.getTipo());
        accountClient.setSaldo_inicial(accountStructure.getSaldo_inicial());
        accountClient.setEstado(accountStructure.getEstado());

        accountClientService.saveAccountClient(accountStructure);
        verify(clientRepository, times(1)).findByName("Jose Lema");
        verify(accountClientRepository, times(1)).save(accountClient);

    }
}
