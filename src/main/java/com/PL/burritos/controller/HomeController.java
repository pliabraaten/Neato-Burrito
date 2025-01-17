package com.PL.burritos.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
