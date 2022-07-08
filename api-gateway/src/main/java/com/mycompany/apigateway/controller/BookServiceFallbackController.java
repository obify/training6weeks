package com.mycompany.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookServiceFallbackController {

    @GetMapping("/bsFallback")
    public String fallBackForBScall(){
        return "Book Service is temporarily unavailable";
    }
}
