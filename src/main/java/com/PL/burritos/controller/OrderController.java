package com.PL.burritos.controller;

import com.PL.burritos.entity.BurritoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j  // Lombok annotation that auto-generates SLF4J (Simple Logging Facade for Java) - Logger
@Controller
@RequestMapping("/orders")  // Handle HTTP requests from path /design
@SessionAttributes("burritoOrder")  // Used to store data in user session to span multiple requests
public class OrderController {

    // ENDPOINTS

    // Submitted form from orderForm are bound to burritoOrder object
    @PostMapping
    // Pass in burrito order object and the sessionstatus object where these objects were put into
    public String processOrder(BurritoOrder order, SessionStatus sessionStatus) {
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();  // End session and ready for next order

        return "redirect:/";
    }

    // Render /current to show current order
    @GetMapping("/current")
    public String orderForm() {

        System.out.println("Made it to GetMapping /current");

        return "orderForm";
    }

}
