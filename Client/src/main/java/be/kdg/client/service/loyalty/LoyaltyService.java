package be.kdg.client.service.loyalty;

import be.kdg.client.domain.account.Client;
import be.kdg.client.domain.loyalty.LoyaltyEvent;
import be.kdg.client.domain.loyalty.LoyaltyLevel;
import be.kdg.client.domain.loyalty.PointsCalculatorInterface;
import be.kdg.client.repository.loyalty.LoyaltyEventRepository;
import be.kdg.client.service.account.AccountService;
import be.kdg.client.service.account.ClientService;
import be.kdg.client.service.dto.LoyaltyLevelDto;
import be.kdg.client.service.dto.LoyaltyPointEvent;
import be.kdg.client.service.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoyaltyService {
    private final ClientService clientService;
    private final AccountService accountService;
    private final LoyaltyEventRepository loyaltyEventRepository;
    private final PointsCalculatorInterface pointsCalculatorInterface;
    private final LoyaltyLevelService loyaltyLevelService;

    public LoyaltyService(ClientService clientService, AccountService accountService, LoyaltyEventRepository loyaltyEventRepository,
                          @Autowired PointsCalculatorInterface pointsCalculatorInterface, LoyaltyLevelService loyaltyLevelService) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.loyaltyEventRepository = loyaltyEventRepository;
        this.pointsCalculatorInterface = pointsCalculatorInterface;
        this.loyaltyLevelService = loyaltyLevelService;
    }

    public String getLoyalty(UUID accountIdFromLoggedInUser) {
        Client client = accountService.getClientByAccountId(accountIdFromLoggedInUser);
        int points = calculatePoints(client.getLoyaltyEvents());
        return loyaltyLevelService.getLoyaltyLevel(points).getName();
    }

    public String getLoyaltyPoints(UUID accountIdFromLoggedInUser) {
        Client client = accountService.getClientByAccountId(accountIdFromLoggedInUser);
        int points = calculatePoints(client.getLoyaltyEvents());
        return String.valueOf(points);
    }

    @EventListener
    private void addLoyaltyPoints(LoyaltyPointEvent loyaltyPointEvent) {
        Client client = accountService.getClientByAccountId(loyaltyPointEvent.accountID());
        int points = pointsCalculatorInterface.calculatePoints(loyaltyPointEvent.price());
        loyaltyEventRepository.save(new LoyaltyEvent(LocalDateTime.now(), points, client));
    }

    public Double getDiscount(long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isEmpty()) {
            throw new AccountNotFoundException("Client not found");
        }
        int points = calculatePoints(client.get().getLoyaltyEvents());
        return loyaltyLevelService.getDiscountPercentage(points);
    }

    public void changeLoyaltyLimits(LoyaltyLevelDto loyaltyLevelDto) {
        loyaltyLevelService.changeLoyaltyLevel(loyaltyLevelDto.getId(), loyaltyLevelDto.getName(),
                    loyaltyLevelDto.getDiscountPercentage(), loyaltyLevelDto.getThreshold());
    }

    public List<LoyaltyLevelDto> getLoyaltyLimits() {
        HashMap<Integer, LoyaltyLevel> loyaltyLevels = loyaltyLevelService.getLoyaltyLevels();
        List<LoyaltyLevelDto> loyaltyLevelDtos = new ArrayList<>();
        for (int i = 0; i < loyaltyLevels.size(); i++) {
            LoyaltyLevel loyaltyLevel = loyaltyLevels.get(i);
            loyaltyLevelDtos.add(new LoyaltyLevelDto(i, loyaltyLevel.getName(),
                    loyaltyLevel.getDiscountPercentage(), loyaltyLevel.getThreshold()));
        }
        return loyaltyLevelDtos;
    }

    public void createLoyaltyLevel(LoyaltyLevelDto loyaltyLevelDto) {
        loyaltyLevelService.createLoyaltyLevel(loyaltyLevelDto.getName(),
                loyaltyLevelDto.getDiscountPercentage(), loyaltyLevelDto.getThreshold());
    }

    public void deleteLoyaltyLevel(int id) {
        loyaltyLevelService.deleteLoyaltyLevel(id);
    }

    private int calculatePoints(List<LoyaltyEvent> loyaltyEvents) {
        return loyaltyEvents.stream().mapToInt(LoyaltyEvent::getPoints).sum();
    }
}
