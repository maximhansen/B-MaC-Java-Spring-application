package be.kdg.client.service.account;

import be.kdg.client.controller.dto.NewAccountDto;
import be.kdg.client.domain.account.Account;
import be.kdg.client.domain.account.Client;
import be.kdg.client.repository.account.AccountRepository;
import be.kdg.client.service.account.exception.AccountExistException;
import be.kdg.client.service.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ClientService clientService;

    public AccountService(AccountRepository accountRepository, ClientService clientService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }

    public Client getClientByAccountId(UUID id) throws AccountNotFoundException{
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty() || account.get().getClient() == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return account.get().getClient();
    }

    public void addClientAccount(UUID accountIdFromLoggedInUser, NewAccountDto newAccountDto) {
        if (accountRepository.findById(accountIdFromLoggedInUser).isPresent()) {
            throw new AccountExistException("Account already exists");
        }
        Client client = new Client();
        clientService.addClient(client);
        Account account = new Account(accountIdFromLoggedInUser, client,
                newAccountDto.getFirstName(), newAccountDto.getLastName(), newAccountDto.getPhoneNumber());
        accountRepository.save(account);
    }

    public void deleteClientAccount(UUID accountIdFromLoggedInUser) {
        Optional<Account> account = accountRepository.findById(accountIdFromLoggedInUser);
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }
        accountRepository.delete(account.get());
    }

    public List<Account> getAccountsThatHasAClientId() {
        Optional<List<Account>> accounts = accountRepository.getAllByClientIsNotNull();
        if(accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found");
        }
        return accounts.get();
    }

    public Account getAccountByClientId(Long clientId) {
        Optional<Account> account = accountRepository.findAccountByClientId(clientId);
        if(account.isEmpty()) {
            throw new AccountNotFoundException("No account found for client: " + clientId);
        }
        return account.get();
    }
}
