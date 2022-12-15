package br.com.client.api.services;

import br.com.client.api.dto.ClientDTO;
import br.com.client.api.entities.Client;
import br.com.client.api.repositories.ClientRepository;
import br.com.client.api.services.exceptions.DataBaseException;
import br.com.client.api.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = new Client(dto);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    public ClientDTO update(Long id, ClientDTO dto) {
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ResourceNotFoundException("Not found id: " + id);
        }

        Client client = optionalClient.get();
        client.setDto(dto);
        client = repository.save(client);
        return new ClientDTO(client);
    }

}
