package be.kdg.client.repository.account;

import be.kdg.client.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<List<Account>> getAllByClientIsNotNull();
    Optional<Account> findAccountByClientId(Long clientId);
}
