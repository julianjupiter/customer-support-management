package com.julianjupiter.csm.service;

import com.julianjupiter.csm.dto.TicketDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public interface TicketService {
    List<TicketDto> findAll();

    Optional<TicketDto> findById(UUID id);
}
