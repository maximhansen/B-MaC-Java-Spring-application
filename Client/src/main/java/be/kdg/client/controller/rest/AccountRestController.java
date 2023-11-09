package be.kdg.client.controller.rest;

import be.kdg.client.controller.dto.NewAccountDto;
import be.kdg.client.controller.rest.security.SecurityUtils;
import be.kdg.client.domain.account.Account;
import be.kdg.client.service.account.AccountService;
import be.kdg.client.service.dto.ClientDto;
import be.kdg.client.service.dto.TransferDtoService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/account")
public class AccountRestController {

    private final AccountService accountService;
    private final TransferDtoService transferDtoService;

    public AccountRestController(AccountService accountService, TransferDtoService transferDtoService) {
        this.accountService = accountService;
        this.transferDtoService = transferDtoService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<String> createAccount(@NotNull @RequestBody NewAccountDto newAccountDto) {
        try {
            accountService.addClientAccount(SecurityUtils.getAccountIdFromLoggedInUser(), newAccountDto);
            log.info("Account created: " + SecurityUtils.getAccountIdFromLoggedInUser());
            return ResponseEntity.ok("Account created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<String> deleteAccount() {
        accountService.deleteClientAccount(SecurityUtils.getAccountIdFromLoggedInUser());
        log.info("Account deleted: " + SecurityUtils.getAccountIdFromLoggedInUser());
        return ResponseEntity.ok("Account deleted");
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAccount() {
        try {
            List<Account> accounts = accountService.getAccountsThatHasAClientId();
            List<ClientDto> clients = transferDtoService.accountsToClientDto(accounts);
            log.info("Accounts retrieved");
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
