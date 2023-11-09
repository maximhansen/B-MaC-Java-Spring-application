package be.kdg.client.service.account;

import be.kdg.client.domain.account.Client;
import be.kdg.client.repository.account.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }
}
