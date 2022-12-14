package br.com.client.api.services;

import br.com.client.api.dto.ClientDTO;
import br.com.client.api.entities.Client;
import br.com.client.api.repositories.ClientRepository;
import br.com.client.api.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(ClientDTO::new);
    }

    public ClientDTO findById(Long id) {
        Optional<Client> optional = repository.findById(id);
        Client client = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDTO(client);
    }
}
