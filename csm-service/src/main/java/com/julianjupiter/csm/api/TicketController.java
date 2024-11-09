package com.julianjupiter.csm.api;

import com.julianjupiter.csm.dto.ApiResponseDto;
import com.julianjupiter.csm.dto.CreateTicketRequestDto;
import com.julianjupiter.csm.exception.ApplicationException;
import com.julianjupiter.csm.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ApiResponseDto findAll() {
        var tickets = this.ticketService.findAll();

        return ApiResponseDto.of(HttpStatus.OK, tickets);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto create(@RequestBody CreateTicketRequestDto createTicketRequestDto) {
        // no create ticket implementation yet in the service
        return ApiResponseDto.of(HttpStatus.CREATED, createTicketRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> findById(@PathVariable UUID id) {
        return this.ticketService.findById(id)
                .map(ticketDto -> ApiResponseDto.of(HttpStatus.OK, ticketDto))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Ticket does not exist"));
    }
}
