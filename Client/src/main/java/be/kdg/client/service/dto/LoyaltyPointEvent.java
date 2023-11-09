package be.kdg.client.service.dto;

import java.util.UUID;

public record LoyaltyPointEvent(Double price, UUID accountID) {
}
