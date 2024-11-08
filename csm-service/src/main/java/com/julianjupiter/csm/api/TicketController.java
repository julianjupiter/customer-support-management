package com.julianjupiter.csm.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    @GetMapping
    public String findAll() {
        return "test";
    }

    @PostMapping
    public String create() {
        return "test";
    }
}
