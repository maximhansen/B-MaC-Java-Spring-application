package be.kdg.bakery.controller;

import be.kdg.bakery.service.api.OrderApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    private final OrderApiService OrderApiService;

    public OrderController(OrderApiService OrderApiService) {
        this.OrderApiService = OrderApiService;
    }

    @GetMapping("/incomingOrders")
    public String showIncomingOrders(Model model) {
        try {
            var orders = OrderApiService.getAllConfirmedOrders();
            model.addAttribute("orders", orders);
            model.addAttribute("error", false);
        } catch (Exception e) {
            model.addAttribute("error", true);
        }
        return "incomingOrders";
    }
}
