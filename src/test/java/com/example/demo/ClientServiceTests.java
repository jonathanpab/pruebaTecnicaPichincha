package com.example.demo;

import com.example.demo.entity.Client;
import com.example.demo.entity.Person;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.ClientService;
import com.example.demo.structure.ClientStructure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClientServiceTests {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    PersonRepository personRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createClientTest() {
        ClientStructure clientStructure = new ClientStructure("Jose Lema", "Otavalo sn y principal",
                "098254785", "1234", true);

        clientService.saveClient(clientStructure);
        Person person = new Person();
        person.setNombre(clientStructure.getNombre());
        person.setDireccion(clientStructure.getDireccion());
        person.setTelefono(clientStructure.getTelefono());
        Client client = new Client();
        client.setContrasena(clientStructure.getContrasena());
        client.setEstado(clientStructure.getEstado());

        verify(personRepository, times(1)).save(person);
        verify(personRepository, times(1)).findByName("Jose Lema");
        verify(clientRepository, times(1)).save(client);
    }
}