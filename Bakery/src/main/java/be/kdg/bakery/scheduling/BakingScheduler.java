package be.kdg.bakery.scheduling;

import be.kdg.bakery.service.BakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BakingScheduler {
    private final BakingService bakingService;

    @Autowired
    public BakingScheduler(BakingService bakingService) {
        this.bakingService = bakingService;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void triggerBaking() {
        bakingService.startBaking();
    }
}
