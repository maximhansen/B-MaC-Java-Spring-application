package be.kdg.client.controller.rest;

import be.kdg.client.controller.rest.security.SecurityUtils;
import be.kdg.client.service.dto.LoyaltyLevelDto;
import be.kdg.client.service.loyalty.LoyaltyService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty")
@Slf4j
public class LoyaltyRestController {

    private final LoyaltyService loyaltyService;

    public LoyaltyRestController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public String getLoyalty() {
        return loyaltyService.getLoyalty(SecurityUtils.getAccountIdFromLoggedInUser());
    }

    @GetMapping("/points")
    @PreAuthorize("hasAuthority('user')")
    public String getLoyaltyPoints() {
        return loyaltyService.getLoyaltyPoints(SecurityUtils.getAccountIdFromLoggedInUser());
    }

    @GetMapping("/limits")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<LoyaltyLevelDto>> getLoyaltyLimits() {
        return ResponseEntity.ok(loyaltyService.getLoyaltyLimits());
    }

    @PostMapping("/limits/change")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> changeLoyaltyLimits(@RequestBody @NotNull LoyaltyLevelDto loyaltyLevelDto) {
        try {
            loyaltyService.changeLoyaltyLimits(loyaltyLevelDto);
            return ResponseEntity.ok("Loyalty limits set");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/limits/create")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> createLoyaltyLimit(@RequestBody @NotNull LoyaltyLevelDto loyaltyLevelDto) {
        try {
            loyaltyService.createLoyaltyLevel(loyaltyLevelDto);
            log.info("Loyalty level created");
            return ResponseEntity.ok("Loyalty level created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/limits/delete")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> deleteLoyaltyLimit(@RequestParam @NotNull int id) {
        try {
            loyaltyService.deleteLoyaltyLevel(id);
            log.info("Loyalty level deleted");
            return ResponseEntity.ok("Loyalty level deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
