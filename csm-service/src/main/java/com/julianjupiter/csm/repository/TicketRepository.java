package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Julian Jupiter
 */
public interface TicketRepository extends JpaRepository<Ticket, String> {
}
