package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.TicketDto;
import com.julianjupiter.csm.mapper.TicketMapper;
import com.julianjupiter.csm.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultTicketService implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    DefaultTicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<TicketDto> findAll() {
        return this.ticketRepository.findAll().stream()
                .map(this.ticketMapper::map)
                .toList();
    }

    @Override
    public Optional<TicketDto> findById(UUID id) {
        return this.ticketRepository.findById(id.toString())
                .map(this.ticketMapper::map);
    }
}
