package br.com.client.api.resources;

import br.com.client.api.entities.Client;
import br.com.client.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientResource {
    @Autowired
    private ClientService clientService;
    @GetMapping
    public List<Client> findAll() {
        return clientService.findPaged();
    }
}
