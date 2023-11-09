package be.kdg.bakery.controller;
import be.kdg.bakery.service.BakingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BakingController {

    private final BakingService bakingService;


    public BakingController(BakingService bakingService) {
        this.bakingService = bakingService;
    }

    @PostMapping("/startBaking")
    public String startBaking() {
        bakingService.startBaking();
        return "redirect:/incomingOrders";
    }
}
